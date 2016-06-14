/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-21 下午6:27:26
 */
public class FifoReadWriteLock implements ReadWriteLock {

    private int readAcquires; // read acquires since start
    private int readReleases; // read releses since start
    private boolean writer; // writer present?
    private Lock metaLock; // short-term synchronization
    private Condition condition;
    private Lock readLock; // readers apply here
    private Lock writeLock; // writers apply here

    public FifoReadWriteLock() {
        readAcquires = readReleases = 0;
        writer = false;
        metaLock = new ReentrantLock();
        condition = metaLock.newCondition();
        readLock = new ReadLock();
        writeLock = new WriteLock();
    }

    public Lock readLock() {
        return readLock;
    }

    public Lock writeLock() {
        return writeLock;
    }

    private class ReadLock implements Lock {
        public void lock() {
            metaLock.lock();
            try {
                readAcquires++;
                while (writer) {
                    try {
                        condition.await();
                    } catch (InterruptedException ex) {
                        // do something application-specific
                    }
                }
            } finally {
                metaLock.unlock();
            }
        }

        public void unlock() {
            metaLock.lock();
            try {
                readReleases++;
                if (readAcquires == readReleases)
                    condition.signalAll();
            } finally {
                metaLock.unlock();
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        public boolean tryLock() {
            throw new UnsupportedOperationException();
        }

        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }

    private class WriteLock implements Lock {
        public void lock() {
            metaLock.lock();
            try {
                while (readAcquires != readReleases)
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                    }
                writer = true;
            } finally {
                metaLock.unlock();
            }
        }

        public void unlock() {
            writer = false;
        }

        public void lockInterruptibly() throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        public boolean tryLock() {
            throw new UnsupportedOperationException();
        }

        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }
    }
}
