/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import com.ouer.solar.able.Entryable;

/**
 * 代表一组键值对
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午10:44:56
 * @param <K>
 * @param <V>
 */

public class KeyAndValue<K, V> extends Entity<KeyAndValue<K, V>> implements Entryable<K, V> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4289336897863408435L;

    /**
     * 键
     */
    protected K key;

    /**
     * 值
     */
    protected V value;

    public KeyAndValue() {

    }

    public KeyAndValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
	public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
	public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    protected Object hashKey() {
        final int prime = 31;
        int result = (key == null) ? 0 : prime * key.hashCode();
        result = (value == null) ? 0 : value.hashCode() + result;
        return result;
    }

    @Override
    protected boolean isEquals(KeyAndValue<K, V> obj) {
        return key.equals(obj.key) && value.equals(obj.value);
    }

}
