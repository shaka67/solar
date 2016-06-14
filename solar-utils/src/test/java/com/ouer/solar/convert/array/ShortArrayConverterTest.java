/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static com.ouer.solar.convert.ConverterTestHelper.arrb;
import static com.ouer.solar.convert.ConverterTestHelper.arri;
import static com.ouer.solar.convert.ConverterTestHelper.arro;
import static com.ouer.solar.convert.ConverterTestHelper.arrs;

import org.junit.Assert;
import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.array.ShortArrayConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:51:24
 */
public class ShortArrayConverterTest {

    @Test
    public void toConvert() {
        ShortArrayConverter shortArrayConverter = (ShortArrayConverter) ConverterManager.lookup(short[].class);

        Assert.assertArrayEquals(arrs(1), shortArrayConverter.toConvert(Double.valueOf(1)));
        Assert.assertArrayEquals(arrs(1, 7, 3), shortArrayConverter.toConvert(arrs(1, 7, 3)));
        Assert.assertArrayEquals(arrs(1, 7, 3), shortArrayConverter.toConvert(arrb(1, 7, 3)));
        Assert.assertArrayEquals(arrs(1, 7, 3), shortArrayConverter.toConvert(arri(1, 7, 3)));
        Assert.assertArrayEquals(arrs(173, 1022), shortArrayConverter.toConvert(arrs("173", "1022")));
        Assert.assertArrayEquals(arrs(173, 1022), shortArrayConverter.toConvert(arrs(" 173 ", " 1022 ")));
        Assert.assertArrayEquals(arrs(173, 10), shortArrayConverter.toConvert(arro("173", Integer.valueOf(10))));
        Assert.assertArrayEquals(arrs(173, 10), shortArrayConverter.toConvert("173,10"));
    }

}
