/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

import java.util.Random;

import com.ouer.solar.RandomUtil;

/**
 * 随机选择路由
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午3:09:20
 */

public class RandomSelector extends LoadBalanceSupport {

    /**
     * 随机生成器
     */
    private Random random = new Random(RandomUtil.next(1, Integer.MAX_VALUE));

    @Override
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int next() {
        int next = RandomUtil.next(random, 0, total - 1);

        return next;
    }

    @Override
    public LoadBalanceStrategy getStrategy() {
        return LoadBalanceStrategy.RANDOM;
    }

}
