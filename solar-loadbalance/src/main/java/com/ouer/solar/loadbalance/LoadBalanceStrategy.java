/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

import com.ouer.solar.loadbalance.calock.RoundRobinCALock;
import com.ouer.solar.loadbalance.calock.RoundWeightCALock;

/**
 * 路由调度策略枚举
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:38:42
 */

public enum LoadBalanceStrategy {
    /**
     * 总是选择第一个服务地址
     */
    CHOOSE_FIRST {
        @Override
        public LoadBalancer getScheduling() {
            return new ChooseFirst();
        }
    },
    /**
     * 不进行同步化的RoundRobin
     */
    NO_SYNC_RR {
        @Override
        public LoadBalancer getScheduling() {
            return new NoSyncRR();
        }
    },
    /**
     * 轮询
     */
    ROUND_ROBIN {
        @Override
        public LoadBalancer getScheduling() {
            return new RoundRobin();
        }
    },
    /**
     * 带权重的轮询
     */
    RR_WITH_WEIGHT {
        @Override
        public LoadBalancer getScheduling() {
            return new RoundWeight();
        }
    },
    /**
     * 哈希值路由
     */
    HASH {
        @Override
        public LoadBalancer getScheduling() {
            return new Hashing();
        }
    },
    /**
     * 随机
     */
    RANDOM {
        @Override
        public LoadBalancer getScheduling() {
            return new RandomSelector();
        }
    },
    /**
     * 使用Composite Abortable Lock的带权重轮询
     */
    COMPOSITE_ABORTABLE_WEIGHT {
        @Override
        public LoadBalancer getScheduling() {
            return new RoundWeightCALock();
        }
    },
    /**
     * 使用Composite Abortable Lock的轮询
     */
    COMPOSITE_ABORTABLE_RR {
        @Override
        public LoadBalancer getScheduling() {
            return new RoundRobinCALock();
        }
    };

    /**
     * 获取路由调度算法
     *
     * @return 路由调度算法 @see Scheduling
     */
    public abstract LoadBalancer getScheduling();

    public static LoadBalanceStrategy fromName(String name) {
    	for (final LoadBalanceStrategy strategy : LoadBalanceStrategy.values()) {
            if (strategy.name().equals(name)) {
                return strategy;
            }
        }
        return null;
    }
}
