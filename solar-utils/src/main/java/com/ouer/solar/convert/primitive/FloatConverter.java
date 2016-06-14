/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import com.ouer.solar.StringUtil;
import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月28日 下午11:12:47
 */
public class FloatConverter extends ObjectConverter<Float> implements TypeConverter<Float> {

    public FloatConverter() {
        register(Float.class);
        register(float.class);
    }

    @Override
    public Float toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Float value) {
        return String.valueOf(value);
    }

    @Override
	public Float toConvert(Object value) {
        if (value.getClass() == Float.class) {
            return (Float) value;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }

        return convert(value.toString());
    }

    private Float convert(String value) {
        try {
            String stringValue = value.trim();
            if (StringUtil.startsWithChar(stringValue, '+')) {
                stringValue = stringValue.substring(1);
            }
            return Float.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
