/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

/**
 * 不进行同步化的RoundRobin
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:45:43
 */

public class NoSyncRR extends LoadBalanceSupport {

    /**
     * 当前索引号
     */
    private int index;

    @Override
    public void setTotal(int total) {
        this.total = total;
        index = 0;
    }

    @Override
    public int next() {
        int next = index++;
        // 溢出处理
        if (next < 0) {
            next = 0;
            index = next;
        }
        return next % total;
    }

    @Override
    public LoadBalanceStrategy getStrategy() {
        return LoadBalanceStrategy.NO_SYNC_RR;
    }

}
