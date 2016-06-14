/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 异步请求处理
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-19 下午5:05:49
 */
public interface AsynProcessor<P, R> {

    /**
     * 处理请求
     * 
     * @param processing 代表一个请求
     */
    void process(P processing);

    /**
     * 返回处理结果，如果还没处理完成或没开始，则返还<code>null</code>
     * 
     * @return 处理结果
     */
    R get();

}
