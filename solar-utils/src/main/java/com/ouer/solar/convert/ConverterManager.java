/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

/**
 * FIXME removed
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午8:00:13
 */
public abstract class ConverterManager {

    private static final ConverterManagerBean CONVERTER_MANAGER_BEAN = ConverterManagerBean.getInstance();

    public static void unregister(Class<?> type) {
        CONVERTER_MANAGER_BEAN.unregister(type);
    }

    public static TypeConverter<?> lookup(Class<?> type) {
        return CONVERTER_MANAGER_BEAN.lookup(type);
    }

    public static <T> T convertType(Object value, Class<T> destinationType) {
        return CONVERTER_MANAGER_BEAN.convertType(value, destinationType);
    }

}