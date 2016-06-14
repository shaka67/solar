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
 * @version create on 2014年7月28日 下午11:13:01
 */
public class IntegerConverter extends ObjectConverter<Integer> implements TypeConverter<Integer> {

    public IntegerConverter() {
        register(Integer.class);
        register(int.class);
    }

    @Override
    public Integer toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Integer value) {
        return String.valueOf(value);
    }

    @Override
	public Integer toConvert(Object value) {
        if (value.getClass() == Integer.class) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return Integer.valueOf(((Number) value).intValue());
        }

        return convert(value.toString());
    }

    private Integer convert(String value) {
        try {
            String stringValue = value.trim();
            if (StringUtil.startsWithChar(stringValue, '+')) {
                stringValue = stringValue.substring(1);
            }
            return Integer.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
