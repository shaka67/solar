/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 装饰用
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-6-17 下午4:04:20
 */
public interface Decoratable<T> {

    T get();

    void set(T t);

}
