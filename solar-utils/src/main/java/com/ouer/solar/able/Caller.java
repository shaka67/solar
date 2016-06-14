/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 无抛出异常的“Callable”
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-11 下午3:29:31
 */
public interface Caller<V> {

    /**
     * Computes a result
     * 
     * @return computed result
     */
    V call();

}
