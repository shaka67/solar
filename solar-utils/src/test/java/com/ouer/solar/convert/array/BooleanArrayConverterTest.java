/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static com.ouer.solar.convert.AssertArraysTestHelper.arrf;
import static com.ouer.solar.convert.AssertArraysTestHelper.arri;
import static com.ouer.solar.convert.AssertArraysTestHelper.arrl;
import static com.ouer.solar.convert.AssertArraysTestHelper.arrs;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.array.BooleanArrayConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午1:52:08
 */
public class BooleanArrayConverterTest {

    @Test
    public void toConvert() {
        BooleanArrayConverter booleanArrayConverter = (BooleanArrayConverter) ConverterManager.lookup(boolean[].class);

        boolean[] primitiveArray = new boolean[] { false, true, false };
        Object convertedArray = booleanArrayConverter.toConvert(primitiveArray);
        assertEquals(boolean[].class, convertedArray.getClass());

        Boolean[] booleanArray = new Boolean[] { Boolean.FALSE, Boolean.TRUE, Boolean.FALSE };
        convertedArray = booleanArrayConverter.toConvert(booleanArray);
        assertEquals(boolean[].class, convertedArray.getClass()); // boolean[]!

        assertEq(arrl(true), booleanArrayConverter.toConvert(Boolean.TRUE));
        assertEq(arrl(true), booleanArrayConverter.toConvert("true"));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(arrl(true, false, true)));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(arri(-7, 0, 3)));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(arrf(-7.0f, 0.0f, 3.0f)));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(arrs("true", "0", "yes")));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(arrs(" true ", "0", " yes ")));
        assertEq(arrl(true, false, true), booleanArrayConverter.toConvert(" true , 0,  yes "));
    }

    @Test
    public void testArrayConversion() {
        Object[] booleanArray = new Object[] { Boolean.FALSE, "TRUE", Integer.valueOf(0) };

        boolean[] arr1 = ConverterManager.convertType(booleanArray, boolean[].class);
        assertEquals(3, arr1.length);
        assertEq(arrl(false, true, false), arr1);

        Boolean[] arr2 = ConverterManager.convertType(booleanArray, Boolean[].class);
        assertEquals(3, arr2.length);
        assertEq(arrl(false, true, false), arr2);
    }

    private void assertEq(boolean[] arr1, boolean[] arr2) {
        assertEquals(arr1.length, arr2.length);
        for (int i = 0; i < arr1.length; i++) {
            assertEquals(arr1[i], arr2[i]);
        }
    }

    private void assertEq(boolean[] arr1, Boolean[] arr2) {
        assertEquals(arr1.length, arr2.length);
        for (int i = 0; i < arr1.length; i++) {
            assertEquals(arr1[i], arr2[i]);
        }
    }

}
