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
 * @version create on 2014年9月19日 下午2:39:02
 */
public class IntegerArrayConverter extends ObjectConverter<int[]> implements TypeConverter<int[]> {

    protected final ConvertBean convertBean;

    public IntegerArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(int[].class);
        typeConverters.put(Integer[].class, new ArrayConverter<Integer>(Integer.class));
    }

    public IntegerArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(int[].class);
        typeConverters.put(Integer[].class, new ArrayConverter<Integer>(Integer.class));
    }

    @Override
    public int[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(int[] value) {
        return Arrays.toString(value);
    }

    @Override
	public int[] toConvert(Object value) {
        Class<?> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new int[] { convertBean.toIntValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Int(value);
        }

        // array
        return convertArray((Object[]) value);
    }

    protected int[] convertArray(Object[] values) {
        int[] results = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = convertBean.toIntValue(values[i]);
        }
        return results;
    }

}