/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

/**
 * 具有排序比较功能的 {@link KeyAndValue KeyAndValue}<br>
 * 通过属性 {@link #index} 进行排序比较
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年10月30日 上午12:38:48
 */

public class SortedKV<K, V> extends KeyAndValue<K, V> {

    /**
     * 
     */
    private static final long serialVersionUID = -2989716549302398171L;

    /**
     * 索引位，用作排序比较
     */
    protected int index;

    public SortedKV() {
        super();
    }

    public SortedKV(K key, V value) {
        super(key, value);
    }

    public SortedKV(K key, V value, int index) {
        super(key, value);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
