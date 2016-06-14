/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

/**
 * 路由调度的简单支持
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午4:11:46
 */

public abstract class LoadBalanceSupport implements LoadBalancer {

    /**
     * 路由总数
     */
    protected int total = 1;

    @Override
    public int getTotal() {
        return total;
    }

}
