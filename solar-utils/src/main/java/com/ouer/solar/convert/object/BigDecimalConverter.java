/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.math.BigDecimal;

import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月7日 下午11:58:26
 */
public class BigDecimalConverter extends ObjectConverter<BigDecimal> implements TypeConverter<BigDecimal> {

    public BigDecimalConverter() {
        register(BigDecimal.class);
    }

    @Override
    public BigDecimal toConvert(String value) {
        return new BigDecimal(value.trim());
    }

    @Override
    public String fromConvert(BigDecimal value) {
        return String.valueOf(value);
    }

    @Override
	public BigDecimal toConvert(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        try {
            return new BigDecimal(value.toString().trim());
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}