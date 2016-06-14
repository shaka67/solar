/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.context;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 虚拟锁，通过<code>Lock</code>的编程习惯确保资源释放
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-6-26 上午2:34:11
 */
public class ThreadContextLock implements Lock {

    private Serializable key;

    public ThreadContextLock(Serializable key) {
        this.key = key;
    }

    @Override
    public void lock() {
        ThreadContext.putShardKey(key);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean tryLock() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void unlock() {
        ThreadContext.clean();
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }

}
