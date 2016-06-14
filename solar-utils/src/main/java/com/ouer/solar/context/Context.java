/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.context;

import java.util.Collection;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月31日 下午10:39:40
 */
public interface Context<K, V> {

    V get(K key);

    Context<K, V> set(K key, V value);

    V getAndRemove(K key);

    Collection<V> values();

    void clear();

}
