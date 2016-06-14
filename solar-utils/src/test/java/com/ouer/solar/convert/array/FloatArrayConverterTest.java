/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static com.ouer.solar.convert.ConverterTestHelper.arrb;
import static com.ouer.solar.convert.ConverterTestHelper.arrd;
import static com.ouer.solar.convert.ConverterTestHelper.arrf;
import static com.ouer.solar.convert.ConverterTestHelper.arri;
import static com.ouer.solar.convert.ConverterTestHelper.arrl;
import static com.ouer.solar.convert.ConverterTestHelper.arro;
import static com.ouer.solar.convert.ConverterTestHelper.arrs;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.array.FloatArrayConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:40:35
 */
public class FloatArrayConverterTest {

    @Test
    public void toConvert() {
        FloatArrayConverter floatArrayConverter = (FloatArrayConverter) ConverterManager.lookup(float[].class);

        assertEq(arrf((float) 1.73), floatArrayConverter.toConvert(Float.valueOf((float) 1.73)));
        assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.toConvert(arrf((float) 1.73, (float) 10.22)));
        assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.toConvert(arrd(1.73, 10.22)));
        assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.toConvert(arrf(1.73f, 10.22f)));
        assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.toConvert(arri(1, 7, 3)));
        assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.toConvert(arrl(1, 7, 3)));
        assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.toConvert(arrb(1, 7, 3)));
        assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.toConvert(arrs(1, 7, 3)));
        assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.toConvert(arrs("1.73", "10.22")));
        assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.toConvert(arrs(" 1.73 ", " 10.22 ")));
        assertEq(arrf((float) 1.73, 10), floatArrayConverter.toConvert(arro("1.73", 10)));
        assertEq(arrf((float) 1.73, 10), floatArrayConverter.toConvert("1.73 \n 10"));
    }

    private void assertEq(float[] arr1, float[] arr2) {
        assertEquals(arr1.length, arr2.length);
        for (int i = 0; i < arr1.length; i++) {
            assertEquals(arr1[i], arr2[i], 0.0001);
        }
    }

}
