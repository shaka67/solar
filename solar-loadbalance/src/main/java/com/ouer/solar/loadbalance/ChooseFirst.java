/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

/**
 * 总是选择第一个服务地址
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:37:50
 */

public class ChooseFirst extends LoadBalanceSupport {

    @Override
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int next() {
        return 0;
    }

    @Override
    public LoadBalanceStrategy getStrategy() {
        return LoadBalanceStrategy.CHOOSE_FIRST;
    }

}
