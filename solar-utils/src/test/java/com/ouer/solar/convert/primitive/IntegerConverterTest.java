/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.primitive.IntegerConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:44:44
 */
public class IntegerConverterTest {

    @Test
    public void toConvert() {
        IntegerConverter integerConverter = new IntegerConverter();

        assertEquals(Integer.valueOf(1), integerConverter.toConvert(Integer.valueOf(1)));
        assertEquals(Integer.valueOf(1), integerConverter.toConvert(Short.valueOf((short) 1)));
        assertEquals(Integer.valueOf(1), integerConverter.toConvert(Double.valueOf(1.0D)));
        assertEquals(Integer.valueOf(1), integerConverter.toConvert("1"));
        assertEquals(Integer.valueOf(1), integerConverter.toConvert(" 1 "));

        assertEquals(Integer.valueOf(1), integerConverter.toConvert(" +1 "));
        assertEquals(Integer.valueOf(-1), integerConverter.toConvert(" -1 "));
        assertEquals(Integer.valueOf(2147483647), integerConverter.toConvert(" +2147483647 "));
        assertEquals(Integer.valueOf(-2147483648), integerConverter.toConvert(" -2147483648 "));

    }
}
