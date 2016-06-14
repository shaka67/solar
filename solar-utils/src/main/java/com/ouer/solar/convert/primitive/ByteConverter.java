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
 * @version create on 2014年7月28日 下午11:11:11
 */
public class ByteConverter extends ObjectConverter<Byte> implements TypeConverter<Byte> {

    public ByteConverter() {
        register(Byte.class);
        register(byte.class);
    }

    @Override
    public Byte toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Byte value) {
        return String.valueOf(value);
    }

    @Override
	public Byte toConvert(Object value) {
        if (value.getClass() == Byte.class) {
            return (Byte) value;
        }
        if (value instanceof Number) {
            return Byte.valueOf(((Number) value).byteValue());
        }

        return convert(value.toString());
    }

    private Byte convert(String value) {
        try {
            String stringValue = value.trim();
            if (StringUtil.startsWithChar(stringValue, '+')) {
                stringValue = stringValue.substring(1);
            }
            return Byte.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
