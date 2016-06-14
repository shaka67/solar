/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 定义返回值接口，一般用于不能继承的对象，如枚举
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月17日 下午5:31:06
 * @param <T>
 */
public interface Valuable<T> {

    /**
     * 取值
     * 
     * @return the value
     */
    T value();
}
