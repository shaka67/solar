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

public class LongArrayConverter extends ObjectConverter<long[]> implements TypeConverter<long[]> {

    protected final ConvertBean convertBean;

    public LongArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(long[].class);
        typeConverters.put(Long[].class, new ArrayConverter<Long>(Long.class));
    }

    public LongArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(long[].class);
        typeConverters.put(Long[].class, new ArrayConverter<Long>(Long.class));
    }

    @Override
    public long[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(long[] value) {
        return Arrays.toString(value);
    }

    @Override
	public long[] toConvert(Object value) {
        Class<?> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new long[] { convertBean.toLongValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Long(value);
        }

        return convertArray((Object[]) value);
    }

    protected long[] convertArray(Object[] values) {
        long[] results = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = convertBean.toLongValue(values[i]);
        }
        return results;
    }

}