/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午1:06:15
 */
public interface Setter {

    void invokeSetter(Object target, Object argument) throws IllegalAccessException, InvocationTargetException;

    Class<?> getSetterRawType();

    Class<?> getSetterRawComponentType();

}