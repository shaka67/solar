/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Lock free的互斥锁
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-21 下午6:25:33
 */
public class Mutex implements Lock {

    private static final int FREE = 0;
    private static final int BUSY = 1;

    private static class LockSynch extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 4689388770786922019L;

        protected boolean isHeldExclusively() {
            return getState() == BUSY;
        }

        public boolean tryAcquire(int acquires) {
            return compareAndSetState(FREE, BUSY);
        }

        protected boolean tryRelease(int releases) {
            if (getState() == FREE) {
                throw new IllegalMonitorStateException();
            }

            setState(FREE);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    private final LockSynch sync = new LockSynch();

    public void lock() {
        sync.acquire(0);
    }

    public boolean tryLock() {
        return sync.tryAcquire(0);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    public void unlock() {
        sync.release(0);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }

}
