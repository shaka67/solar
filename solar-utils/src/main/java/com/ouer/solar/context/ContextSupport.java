/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.context;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;

import com.ouer.solar.CollectionUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月31日 下午10:41:40
 */
public class ContextSupport<K, V> implements Context<K, V> {

    private ConcurrentMap<K, V> map = CollectionUtil.createConcurrentMap();

    public static final <K, V> Context<K, V> createContext() {
        return new ContextSupport<K, V>();
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public Context<K, V> set(K key, V value) {
        map.put(key, value);

        return this;
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    @Override
    public V getAndRemove(K key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

}
