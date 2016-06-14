/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.math.BigInteger;

import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月7日 下午11:53:48
 */
public class BigIntegerConverter extends ObjectConverter<BigInteger> implements TypeConverter<BigInteger> {

    public BigIntegerConverter() {
        register(BigInteger.class);
    }

    @Override
    public BigInteger toConvert(String value) {
        return new BigInteger(value.trim());
    }

    @Override
    public String fromConvert(BigInteger value) {
        return String.valueOf(value);
    }

    @Override
	public BigInteger toConvert(Object value) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof Number) {
            return new BigInteger(String.valueOf(((Number) value).longValue()));
        }
        try {
            return new BigInteger(value.toString().trim());
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
