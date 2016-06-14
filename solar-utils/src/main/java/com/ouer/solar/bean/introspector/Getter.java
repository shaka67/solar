/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午8:39:19
 */
public interface Getter {

    Object invokeGetter(Object target) throws InvocationTargetException, IllegalAccessException;

    Class<?> getGetterRawType();

    Class<?> getGetterRawComponentType();

    Class<?> getGetterRawKeyComponentType();

}