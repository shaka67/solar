/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

/**
 * Object Holder
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-19 下午10:56:54
 */
public class Holder<T> extends StringableSupport {

    private T value;

    public Holder() {

    }

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
