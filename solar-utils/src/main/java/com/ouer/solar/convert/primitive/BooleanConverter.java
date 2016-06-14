/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import static com.ouer.solar.StringPool.Word.FALSE;
import static com.ouer.solar.StringPool.Word.N;
import static com.ouer.solar.StringPool.Word.NO;
import static com.ouer.solar.StringPool.Word.OFF;
import static com.ouer.solar.StringPool.Word.ON;
import static com.ouer.solar.StringPool.Word.ONE;
import static com.ouer.solar.StringPool.Word.TRUE;
import static com.ouer.solar.StringPool.Word.Y;
import static com.ouer.solar.StringPool.Word.YES;
import static com.ouer.solar.StringPool.Word.ZERO;

import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月28日 下午11:11:20
 */
public class BooleanConverter extends ObjectConverter<Boolean> implements TypeConverter<Boolean> {

    public BooleanConverter() {
        register(Boolean.class);
        register(boolean.class);
    }

    @Override
    public Boolean toConvert(String value) {
        return convert(value.trim());
    }

    @Override
    public String fromConvert(Boolean value) {
        return String.valueOf(value);
    }

    @Override
	public Boolean toConvert(Object value) {
        if (value.getClass() == Boolean.class) {
            return (Boolean) value;
        }

        String stringValue = value.toString().trim();
        return convert(stringValue);
    }

    private Boolean convert(String value) {
        if (value.equalsIgnoreCase(YES) || value.equalsIgnoreCase(Y) || value.equalsIgnoreCase(TRUE)
                || value.equalsIgnoreCase(ON) || value.equalsIgnoreCase(ONE)) {
            return Boolean.TRUE;
        }
        if (value.equalsIgnoreCase(NO) || value.equalsIgnoreCase(N) || value.equalsIgnoreCase(FALSE)
                || value.equalsIgnoreCase(OFF) || value.equalsIgnoreCase(ZERO)) {
            return Boolean.FALSE;
        }

        throw new ConvertException(value);
    }

}
