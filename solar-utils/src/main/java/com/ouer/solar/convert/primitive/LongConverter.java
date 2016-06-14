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
 * @version create on 2014年7月28日 下午11:13:18
 */
public class LongConverter extends ObjectConverter<Long> implements TypeConverter<Long> {

    public LongConverter() {
        register(Long.class);
        register(long.class);
    }

    @Override
    public Long toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Long value) {
        return String.valueOf(value);
    }

    @Override
	public Long toConvert(Object value) {
        if (value.getClass() == Long.class) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }

        return convert(value.toString());
    }

    private Long convert(String value) {
        try {
            String stringValue = value.trim();
            if (StringUtil.startsWithChar(stringValue, '+')) {
                stringValue = stringValue.substring(1);
            }
            return Long.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
