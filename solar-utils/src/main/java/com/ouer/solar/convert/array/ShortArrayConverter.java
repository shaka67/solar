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
 * @version create on 2014年9月19日 下午2:41:29
 */
public class ShortArrayConverter extends ObjectConverter<short[]> implements TypeConverter<short[]> {

    protected final ConvertBean convertBean;

    public ShortArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(short[].class);
        typeConverters.put(Short[].class, new ArrayConverter<Short>(Short.class));
    }

    public ShortArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(short[].class);
        typeConverters.put(Short[].class, new ArrayConverter<Short>(Short.class));
    }

    @Override
    public short[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(short[] value) {
        return Arrays.toString(value);
    }

    @Override
	public short[] toConvert(Object value) {
        Class<?> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new short[] { convertBean.toShortValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Short(value);
        }

        return convertArray((Object[]) value);
    }

    protected short[] convertArray(Object[] values) {
        short[] results = new short[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = convertBean.toShortValue(values[i]);
        }
        return results;
    }

}