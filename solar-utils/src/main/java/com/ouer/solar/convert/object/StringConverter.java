/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月28日 下午11:15:07
 */
public class StringConverter extends ObjectConverter<String> implements TypeConverter<String> {

    public StringConverter() {
        register(String.class);
    }

    @Override
    public String toConvert(String value) {
        return value;
    }

    @Override
    public String fromConvert(String value) {
        return value;
    }

    @Override
	public String toConvert(Object value) {
        if (value instanceof CharSequence) { // for speed
            return value.toString();
        }
        Class<?> type = value.getClass();
        if (type == Class.class) {
            return ((Class<?>) value).getName();
        }

        return ArrayUtil.toString(value);
    }

}
