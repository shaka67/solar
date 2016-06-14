/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mutable;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午4:28:44
 */
public class ValueHolder<T> {

    protected T value;

    public ValueHolder() {
    }

    public ValueHolder(T value) {
        this.value = value;
    }

    /**
     * Returns value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets new value.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns <code>true</code> if value is <code>null</code>.
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * Simple to-string representation.
     */
    @Override
    public String toString() {
        if (value == null) {
            return "{" + null + '}';
        }
        return '{' + value.toString() + '}';
    }

}
