/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 同步处理器
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月6日 下午10:18:32
 */
public interface Processor<P, R> {

    /**
     * 处理请求并返回结果
     * 
     * @param processing 待处理的请求
     * @return 处理结果
     */
    R processAndGet(P processing);

}
