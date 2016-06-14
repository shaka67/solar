/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.primitive.LongConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:46:56
 */
public class LongConverterTest {

    @Test
    public void toConvert() {
        LongConverter longConverter = new LongConverter();

        assertEquals(Long.valueOf(173), longConverter.toConvert(Long.valueOf(173)));

        assertEquals(Long.valueOf(173), longConverter.toConvert(Integer.valueOf(173)));
        assertEquals(Long.valueOf(173), longConverter.toConvert(Short.valueOf((short) 173)));
        assertEquals(Long.valueOf(173), longConverter.toConvert(Double.valueOf(173.0D)));
        assertEquals(Long.valueOf(173), longConverter.toConvert(Float.valueOf(173.0F)));
        assertEquals(Long.valueOf(173), longConverter.toConvert("173"));
        assertEquals(Long.valueOf(173), longConverter.toConvert(" 173 "));

        assertEquals(Long.valueOf(-1), longConverter.toConvert(" -1 "));
        assertEquals(Long.valueOf(1), longConverter.toConvert(" +1 "));
        assertEquals(Long.valueOf(9223372036854775807L), longConverter.toConvert(" +9223372036854775807 "));
        assertEquals(Long.valueOf(-9223372036854775808L), longConverter.toConvert(" -9223372036854775808 "));

    }

}
