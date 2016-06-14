/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.concurrent;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 无锁的阻塞队列，无锁算法详见下
 * <p>
 * This implementation employs an efficient &quot;wait-free&quot; algorithm based on one described in <a
 * href="http://www.cs.rochester.edu/u/michael/PODC96.html"> Simple, Fast, and Practical Non-Blocking and Blocking
 * Concurrent Queue Algorithms</a> by Maged M. Michael and Michael L. Scott.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-12 上午1:05:22
 */
public class LockFreeBlockingQueue<T> extends ConcurrentLinkedQueue<T> implements BlockingQueue<T> {

    private static final long serialVersionUID = 3729922749152127548L;

    /**
     * 容量
     */
    private final int capacity;
    /**
     * 总数
     */
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * 无数据时被锁
     */
    private final Lock notEmptyLock = new ReentrantLock();

    /**
     * 无数据时被锁的条件
     */
    private final Condition notEmpty = notEmptyLock.newCondition();

    /**
     * 无数据时被锁等待线程数
     */
    private int notEmptyAwaits;

    public LockFreeBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean offer(T t) {
        boolean retval = super.offer(t);
        if (retval) {
            count.incrementAndGet();
        }

        return retval;
    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        return offer(t);
    }

    @Override
    public T take() throws InterruptedException {
        T val = super.poll();
        if (val != null) {
            decrCount();
            return val;
        }

        waitForNotEmpty();

        val = super.poll();
        if (val != null) {
            decrCount();
        }

        return val;
    }

    @Override
    public T poll() {
        T val = super.poll();
        if (val != null) {
            decrCount();
        }

        return val;
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        long sleepTimeNanos = TimeUnit.NANOSECONDS.convert(timeout, unit);
        long targetTimeNanos = System.nanoTime() + sleepTimeNanos;
        sleepTimeNanos /= 5;
        T el = null;

        while (System.nanoTime() < targetTimeNanos) {
            if ((el = poll()) != null) {
                return el;
            }

            LockSupport.parkNanos(sleepTimeNanos);
        }

        return el;
    }

    @Override
    public boolean remove(Object o) {
        boolean retval = super.remove(o);
        if (retval) {
            decrCount();
        }

        return retval;
    }

    @Override
    public int remainingCapacity() {
        return capacity - size();
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        int cnt = 0;
        if (c == null) {
            return cnt;
        }

        for (;;) {
            T el = poll();
            if (el == null) {
                break;
            }

            c.add(el);
            cnt++;
        }

        count.set(0);
        return cnt;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return drainTo(c);
    }

    @Override
    public void put(T t) throws InterruptedException {
        if (super.offer(t)) {
            incrCount();
        }

    }

    @Override
    public int size() {
        return count.get();
    }

    /**
     * 无数据时等待
     * 
     * @throws InterruptedException
     */
    protected void waitForNotEmpty() throws InterruptedException {
        while (count.get() == 0) {
            notEmptyLock.lock();
            try {
                if (count.get() > 0) {
                    return;
                }

                notEmptyAwaits++;
                notEmpty.await();
            } finally {
                notEmptyLock.unlock();
            }
        }
    }

    /**
     * 总数减一
     */
    protected void decrCount() {
        count.getAndDecrement();
    }

    /**
     * 总数加一
     */
    protected void incrCount() {
        int prevCount = count.getAndIncrement();
        if (prevCount == 0) {
            notEmptyLock.lock();
            try {
                notEmpty.signal(); // not signalAll() as there is only *one* consumer !
            } finally {
                notEmptyLock.unlock();
            }
        }
    }

}
