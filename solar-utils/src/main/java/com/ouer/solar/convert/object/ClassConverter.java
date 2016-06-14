/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import com.ouer.solar.ClassLoaderUtil;
import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午2:56:42
 */
public class ClassConverter extends ObjectConverter<Class<?>> implements TypeConverter<Class<?>> {

    public ClassConverter() {
        typeConverters.put(Class.class, this);
    }

    @Override
    public Class<?> toConvert(String value) {
        try {
            return ClassLoaderUtil.loadClass(value);
        } catch (ClassNotFoundException e) {
            throw new ConvertException(value, e);
        }
    }

    @Override
    public String fromConvert(Class<?> value) {
        return String.valueOf(value);
    }

    @Override
	public Class<?> toConvert(Object value) {
        if (value.getClass() == Class.class) {
            return (Class<?>) value;
        }
        try {
            return ClassLoaderUtil.loadClass(value.toString().trim());
        } catch (ClassNotFoundException e) {
            throw new ConvertException(value, e);
        }
    }

}