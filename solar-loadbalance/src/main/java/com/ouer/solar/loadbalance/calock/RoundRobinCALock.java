/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance.calock;

import java.util.concurrent.locks.Lock;

import com.ouer.solar.concurrent.CompositeFastPathLock;
import com.ouer.solar.loadbalance.LoadBalanceStrategy;
import com.ouer.solar.loadbalance.LoadBalanceSupport;

/**
 * 使用Composite Abortable Lock的轮询策略，提升并发性能
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:47:45
 */

public class RoundRobinCALock extends LoadBalanceSupport {

    /**
     * 当前索引号
     */
    private int index;

    /**
     * Composite Abortable Lock
     */
    private final Lock lock = new CompositeFastPathLock();

    @Override
    public void setTotal(int total) {
        this.total = total;
        index = 0;
    }

    @Override
    public int next() {
        lock.lock();
        try {
            int next = index++;
            // 溢出处理
            if (next < 0) {
                next = 0;
                index = next;
            }
            return next % total;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public LoadBalanceStrategy getStrategy() {
        return LoadBalanceStrategy.COMPOSITE_ABORTABLE_RR;
    }

}
