/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

/**
 * 路由调度算法
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午2:38:02
 */

public interface LoadBalancer {

    /**
     * 获取路由总数
     * 
     * @return 总数
     */
    int getTotal();

    /**
     * 设置路由总数
     * 
     * @param total 总数
     */
    void setTotal(int total);

    /**
     * 返回下一请求的路由索引
     * 
     * @return 路由索引
     */
    int next();

    /**
     * 调度策略枚举，将<code>SchedulingStrategy</code>绑到调度接口上
     * 
     * @return 调度策略枚举@see SchedulingStrategy
     */
    LoadBalanceStrategy getStrategy();
}
