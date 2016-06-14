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
 * @version create on 2014年9月19日 下午2:37:27
 */
public class FloatArrayConverter extends ObjectConverter<float[]> implements TypeConverter<float[]> {

    protected final ConvertBean convertBean;

    public FloatArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(float[].class);
        typeConverters.put(Float[].class, new ArrayConverter<Float>(Float.class));
    }

    public FloatArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(float[].class);
        typeConverters.put(Float[].class, new ArrayConverter<Float>(Float.class));
    }

    @Override
    public float[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(float[] value) {
        return Arrays.toString(value);
    }

    @Override
	public float[] toConvert(Object value) {
        Class<?> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new float[] { convertBean.toFloatValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Float(value);
        }

        return convertArray((Object[]) value);
    }

    protected float[] convertArray(Object[] values) {
        float[] results = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = convertBean.toFloatValue(values[i]);
        }
        return results;
    }

}