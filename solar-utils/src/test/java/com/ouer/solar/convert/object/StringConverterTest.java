/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import static com.ouer.solar.convert.ConverterTestHelper.arrb;
import static com.ouer.solar.convert.ConverterTestHelper.arrc;
import static com.ouer.solar.convert.ConverterTestHelper.arrs;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.object.StringConverter;

public class StringConverterTest {

    // FIXME
    @Test
    public void toConvert() {
        StringConverter stringConverter = new StringConverter();

        assertEquals("123", stringConverter.toConvert("123"));
        // FIXME
        assertEquals("[65, 66]", stringConverter.toConvert(arrb(65, 66)));
        assertEquals("[A, b]", stringConverter.toConvert(arrc('A', 'b')));
        assertEquals("[A, b]", stringConverter.toConvert(arrc('A', 'b')));
        assertEquals("[One, two]", stringConverter.toConvert(arrs("One", "two")));
        assertEquals("123", stringConverter.toConvert(Integer.valueOf(123)));
        assertEquals("java.lang.String", stringConverter.toConvert(String.class));
    }
}
