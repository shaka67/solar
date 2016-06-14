/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

import java.util.concurrent.Callable;

/**
 * 计算接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月22日 下午11:16:27
 */
public interface Computable<K, V> {

    /**
     * 通过关键字来计算
     * 
     * @param key 查找关键字
     * @param callable # @see Callable
     * @return 计算结果
     */
    V get(K key, Callable<V> callable);

}
