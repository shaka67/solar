/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import com.ouer.solar.convert.object.BigIntegerConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午1:51:54
 */
public class BigIntegerConverterTest {

    @Test
    public void toConvert() {
        BigIntegerConverter bigIntegerConverter = new BigIntegerConverter();

        assertEquals(new BigInteger("12345"), bigIntegerConverter.toConvert(new BigInteger("12345")));
        assertEquals(new BigInteger("12345"), bigIntegerConverter.toConvert("12345"));
        assertEquals(new BigInteger("12345"), bigIntegerConverter.toConvert(" 12345 "));
        assertEquals(new BigInteger("12345"), bigIntegerConverter.toConvert(Double.valueOf(12345.0D)));
        assertEquals(new BigInteger("123456789"), bigIntegerConverter.toConvert(Long.valueOf(123456789)));
    }
}
