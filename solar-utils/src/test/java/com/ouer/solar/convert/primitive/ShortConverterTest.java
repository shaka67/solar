/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.primitive.ShortConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午3:05:59
 */
public class ShortConverterTest {

    @Test
    public void toConvert() {
        ShortConverter shortConverter = new ShortConverter();

        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert(Short.valueOf((short) 1)));
        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert(Integer.valueOf(1)));
        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert(Double.valueOf(1.0D)));
        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert("1"));
        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert(" 1 "));

        assertEquals(Short.valueOf((short) 1), shortConverter.toConvert(" +1 "));
        assertEquals(Short.valueOf((short) -1), shortConverter.toConvert(" -1 "));
        assertEquals(Short.valueOf((short) 32767), shortConverter.toConvert(" +32767 "));
        assertEquals(Short.valueOf((short) -32768), shortConverter.toConvert(" -32768 "));

    }
}
