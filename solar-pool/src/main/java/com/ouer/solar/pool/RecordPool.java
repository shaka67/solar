/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RecordPool<T> implements IRecordPool<T> {

	private static final Logger LOG = LoggerFactory.getLogger(RecordPool.class);
	
    private final BlockingQueue<T> queue;
    private int batchSize = 500;
    private final Lock lock = new ReentrantLock();

    public RecordPool(int poolSize) {
        queue = new LinkedBlockingQueue<T>(poolSize);
    }

    @Override
    public boolean add(T rec) {
        boolean ret = false;
        if (rec != null && !queue.contains(rec)) {
            ret = queue.offer(rec);
            if (!ret) {
            	LOG.warn("add the object to the queue failed, the queue may be full.");
            }
        }

        return ret;
    }

    @Override
    public List<T> asList() {
        final List<T> recordsCopy = Lists.newArrayList();
        if (queue.size() > 0) {
            lock.lock();
            try {
                final int num = queue.size() >= batchSize ? batchSize : queue.size();
                queue.drainTo(recordsCopy, num);
            } finally {
                lock.unlock();
            }
        }

        return recordsCopy;
    }

    @Override
    public List<T> getWholeRecords() {
        final List<T> recordsCopy = Lists.newArrayList();
        lock.lock();
        try {
            final int num = queue.size();
            if (num != 0) {
                queue.drainTo(recordsCopy, num);
            }

            return recordsCopy;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public int remainCapacity() {
        return this.queue.remainingCapacity();
    }

    @Override
    public int size() {
        return this.queue.size();
    }
}
