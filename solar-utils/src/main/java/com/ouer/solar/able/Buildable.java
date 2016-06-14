/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 构造接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:24:38
 * @param <T>
 */
public interface Buildable<T> {

    /**
     * 构造对象
     * 
     * @return 构造对象
     */
    T build();

}
