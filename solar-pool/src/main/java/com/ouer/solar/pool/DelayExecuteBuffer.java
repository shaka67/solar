/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ouer.solar.ClassUtil;
import com.ouer.solar.ConcurrentUtil;
import com.ouer.solar.lang.SimpleThreadFactory;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DelayExecuteBuffer<T> implements Buffer<T> {

	private static final Logger LOG = LoggerFactory.getLogger(DelayExecuteBuffer.class);

    private String name;
    private IBatchExecutor<T> batchExecutor;
    private final AtomicReference<RecordPool<T>> recordPool = new AtomicReference<RecordPool<T>>();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new SimpleThreadFactory(
            ClassUtil.getShortClassName(DelayExecuteBuffer.class) + "-single"));

    private long checkInterval = 1000;
    private int poolSize = 1024;
    private int batchSize = 10;
    private int threads = 1;
    private boolean active;

    private ExceptionListener<T> exceptionListener;
    private final Lock lock = new ReentrantLock();

    public DelayExecuteBuffer() {
        this.name = this.getClass().getSimpleName();
    }

    public DelayExecuteBuffer(String name) {
        this.name = name;
    }

    @Override
    public boolean add(List<T> records) {
        lock.lock();
        try {
            for (final T record : records) {
                if (!full()) {
                    final boolean ret = getRecordPool().add(record);
                    if (LOG.isTraceEnabled()) {
	                    LOG.trace("add record to pool [{}]. poolSize=[{}], remainCapacity=[{}], record=[{}], ret=[{}]",
	                            new Object[] { name, poolSize, getRecordPool().remainCapacity(), record, ret });
                    }
                    if (!ret) {
                        return false;
                    }

                } else {
                	LOG.warn("record pool [{}] is full.", name);
                    return false;
                }
            }

        } finally {
            lock.unlock();
        }

        return true;
    }

    @Override
    public boolean add(T record) {
        lock.lock();
        try {
            if (!full()) {
                final boolean ret = getRecordPool().add(record);
                if (LOG.isTraceEnabled()) {
                	LOG.trace("add record to pool [{}]. poolSize=[{}], remainCapacity=[{}], record=[{}], ret=[{}]",
                			new Object[] { name, poolSize, getRecordPool().remainCapacity(), record, ret });
                }
                return ret;
            } else {
            	LOG.warn("record pool [{}] is full.", name);
                return false;
            }
        } finally {
            lock.unlock();
        }

    }

    public void flush() {
        lock.lock();
        try {
            final List<T> records = getRecordPool().getWholeRecords();
            execute(records);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void start() {
        this.recordPool.set(new RecordPool<T>(poolSize));
        this.recordPool.get().setBatchSize(batchSize);

        for (int i = 0; i < threads; i++) {
            scheduler.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                	if (LOG.isTraceEnabled()) {
	                	LOG.trace("schedule: pop from record pool [{}]. poolSize=[{}], remainCapacity=[{}]",
	                            new Object[] { name, poolSize, getRecordPool().remainCapacity() });
                	}

                    final List<T> records = getRecordPool().asList();

                    if (!records.isEmpty()) {
                    	if (LOG.isTraceEnabled()) {
                    		LOG.trace(
	                                "schedule: pop from record pool [{}]. poolSize=[{}], remainCapacity=[{}], size=[{}]",
	                                new Object[] { name, poolSize, getRecordPool().remainCapacity(), records.size() });
                    	}
                        execute(records);
                    }

                }
            }, checkInterval, checkInterval, TimeUnit.MILLISECONDS);
        }

        active = true;
    }

    @Override
    public void stop() {
        active = false;
        flush();
        ConcurrentUtil.shutdownAndAwaitTermination(scheduler);
    }

    private void execute(List<T> records) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        if (LOG.isDebugEnabled()) {
        	LOG.debug("Execute records. size=[{}]", records.size());
        }

        try {
            batchExecutor.execute(records);
        } catch (final Exception e) {
        	LOG.error("", e);
            if (exceptionListener != null) {
            	LOG.error("Execute records failed, calling exception listener...pool=[{}]", name);
                exceptionListener.onException(records);
            }
        }
    }

    public boolean full() {
        return getRecordPool().remainCapacity() == 0;
    }

    public int size() {
        return getRecordPool().size();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordPool<T> getRecordPool() {
        return recordPool.get();
    }

    public void setThreads(int threads) {
        this.threads = threads;
        if (scheduler != null) {
            ConcurrentUtil.shutdownAndAwaitTermination(scheduler);
        }

        this.scheduler =
                Executors.newScheduledThreadPool(threads,
                        new SimpleThreadFactory(ClassUtils.getShortClassName(DelayExecuteBuffer.class) + "-multi"));
    }

    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setExceptionListener(ExceptionListener<T> exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    public void setBatchExecutor(IBatchExecutor<T> batchExecutor) {
        this.batchExecutor = batchExecutor;
    }

}
