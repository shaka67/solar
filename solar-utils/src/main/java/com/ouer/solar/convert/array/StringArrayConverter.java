/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.lang.PrimitiveArrayType;

/**
 * FIXME ArrayConverter
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午3:46:50
 */
@TypeConverter.Convert
public class StringArrayConverter extends ArrayConverter<String> implements TypeConverter<String[]> {

    public StringArrayConverter() {
        register(String[].class);
    }

    @Override
    protected String[] createArray(int length) {
        return new String[length];
    }

    @Override
    protected String[] convertPrimitiveArrayToArray(Object value, Class<?> primitiveComponentType) {
        PrimitiveArrayType arrayType = PrimitiveArrayType.find(value.getClass());
        if (arrayType != null) {
            return arrayType.convert2String(value);
        }

        return null;
    }

}
