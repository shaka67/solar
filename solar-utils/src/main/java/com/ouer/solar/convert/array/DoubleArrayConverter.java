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
 * @version create on 2014年9月19日 下午2:36:11
 */
public class DoubleArrayConverter extends ObjectConverter<double[]> implements TypeConverter<double[]> {

    protected final ConvertBean convertBean;

    public DoubleArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(double[].class);
        typeConverters.put(Double[].class, new ArrayConverter<Double>(Double.class));
    }

    public DoubleArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(double[].class);
        typeConverters.put(Double[].class, new ArrayConverter<Double>(Double.class));
    }

    @Override
    public double[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(double[] value) {
        return Arrays.toString(value);
    }

    @Override
	public double[] toConvert(Object value) {
        Class<?> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new double[] { convertBean.toDoubleValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Double(value);
        }

        return convertArray((Object[]) value);
    }

    protected double[] convertArray(Object[] values) {
        double[] results = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = convertBean.toDoubleValue(values[i]);
        }
        return results;
    }

}