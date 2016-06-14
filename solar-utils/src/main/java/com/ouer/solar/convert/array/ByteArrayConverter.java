/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import java.util.Arrays;

import com.ouer.solar.StringUtil;
import com.ouer.solar.convert.ConvertBean;
import com.ouer.solar.convert.ConvertUtil;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.lang.PrimitiveArrayType;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午2:23:39
 */
public class ByteArrayConverter extends ObjectConverter<byte[]> implements TypeConverter<byte[]> {

    protected final ConvertBean convertBean;

    public ByteArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(byte[].class);
        typeConverters.put(Byte[].class, new ArrayConverter<Byte>(Byte.class));
    }

    public ByteArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(byte[].class);
        typeConverters.put(Byte[].class, new ArrayConverter<Byte>(Byte.class));
    }

    @Override
    public byte[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(byte[] value) {
        return Arrays.toString(value);
    }

    @Override
	public byte[] toConvert(Object value) {
        Class<?> type = value.getClass();

        if (!type.isArray()) {
            return convert(value, type);
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Byte(value);
        }

        return convertArray((Object[]) value);
    }

    private byte[] convert(Object value, Class<?> type) {
        if (type == String.class) {
            String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
            return convertArray(values);
        }

        return new byte[] { convertBean.toByteValue(value) };
    }

    protected byte[] convertArray(Object[] values) {
        byte[] result = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = convertBean.toByteValue(values[i]);
        }
        return result;
    }

}