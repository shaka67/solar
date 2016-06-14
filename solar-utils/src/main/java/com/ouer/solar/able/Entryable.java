/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 定义<code>Entry</code>，一般代表键值对
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:28:40
 * @param <K> key
 * @param <V> value
 */
public interface Entryable<K, V> {

    /**
     * return the key of entry
     * 
     * @return key
     */
    K getKey();

    /**
     * return the value of entry
     * 
     * @return value
     */
    V getValue();

}
