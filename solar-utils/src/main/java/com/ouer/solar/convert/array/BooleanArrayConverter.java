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
 * @version create on 2014年9月19日 上午3:56:53
 */
public class BooleanArrayConverter extends ObjectConverter<boolean[]> implements TypeConverter<boolean[]> {

    protected final ConvertBean convertBean;

    public BooleanArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        register(boolean[].class);
        typeConverters.put(Boolean[].class, new ArrayConverter<Boolean>(Boolean.class));
    }

    public BooleanArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        register(boolean[].class);
        typeConverters.put(Boolean[].class, new ArrayConverter<Boolean>(Boolean.class));
    }

    @Override
    public boolean[] toConvert(String value) {
        String[] values = StringUtil.splitc(value, ArrayConverter.NUMBER_DELIMITERS);
        return convertArray(values);
    }

    @Override
    public String fromConvert(boolean[] value) {
        return Arrays.toString(value);
    }

    @Override
	public boolean[] toConvert(Object value) {
        Class<? extends Object> type = value.getClass();
        if (!type.isArray()) {
            if (type == String.class) {
                String[] values = StringUtil.splitc(value.toString(), ArrayConverter.NUMBER_DELIMITERS);
                return convertArray(values);
            }

            return new boolean[] { convertBean.toBooleanValue(value) };
        }

        PrimitiveArrayType arrayType = PrimitiveArrayType.find(type);
        if (arrayType != null) {
            return arrayType.convert2Boolean(value);
        }

        return convertArray((Object[]) value);
    }

    // FIXME
    private boolean[] convertArray(Object[] values) {
        boolean[] result = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = convertBean.toBooleanValue(values[i]);
        }
        return result;
    }

}