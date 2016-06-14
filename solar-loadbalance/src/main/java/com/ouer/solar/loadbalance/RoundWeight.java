/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.ouer.solar.CollectionUtil;

/**
 * 根据服务权重数轮询路由
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:49:49
 */

public class RoundWeight extends LoadBalanceSupport {

    /**
     * 当前索引号
     */
    private AtomicInteger index = new AtomicInteger(0);

    /**
     * 服务索引到权重的映射表
     */
    private Map<Integer, Integer> weightsMap;

    @Override
    public int next() {
        int next = index.getAndIncrement();
        // 溢出处理
        if (next < 0) {
            next = 0;
            index.set(next);
        }
        return weightsMap.get(next % total);
    }

    @Override
    public int getTotal() {
        return weightsMap.size();
    }

    /**
     * 通过服务权重数初始化 {@link #total} 和 {@link #weightsMap}
     * 
     * @param weights 权重数组
     */
    public void setWeights(int[] weights) {
        int length = weights.length;

        weightsMap = CollectionUtil.createHashMap(length);
        int index = 0;

        for (int i = 0; i < length; i++) {
            int weight = weights[i];
            total += weight;
            int max = index + weight;
            for (; index < max; index++) {
                weightsMap.put(index, i);
            }
        }

        this.index.set(0);
    }

    @Override
    public void setTotal(int total) {
        // ignore
    }

    @Override
    public LoadBalanceStrategy getStrategy() {
        return LoadBalanceStrategy.RR_WITH_WEIGHT;
    }

}
