/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.primitive.ByteConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:14:41
 */
public class ByteConverterTest {

    @Test
    public void toConvert() {
        ByteConverter byteConverter = new ByteConverter();

        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert(Integer.valueOf(1)));
        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert(Short.valueOf((short) 1)));
        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert(Double.valueOf(1.5D)));
        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert("1"));
        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert("  1  "));
        assertEquals(Byte.valueOf((byte) 1), byteConverter.toConvert("  +1  "));
        assertEquals(Byte.valueOf((byte) 127), byteConverter.toConvert("  +127  "));
        assertEquals(Byte.valueOf((byte) -1), byteConverter.toConvert("  -1  "));
        assertEquals(Byte.valueOf((byte) -128), byteConverter.toConvert("  -128  "));
        assertEquals(Byte.valueOf((byte) (300 - 256)), byteConverter.toConvert(Integer.valueOf(300)));

    }
}
