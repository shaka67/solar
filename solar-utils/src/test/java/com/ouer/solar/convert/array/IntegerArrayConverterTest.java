/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static com.ouer.solar.convert.ConverterTestHelper.arrd;
import static com.ouer.solar.convert.ConverterTestHelper.arrf;
import static com.ouer.solar.convert.ConverterTestHelper.arri;
import static com.ouer.solar.convert.ConverterTestHelper.arrl;
import static com.ouer.solar.convert.ConverterTestHelper.arro;
import static com.ouer.solar.convert.ConverterTestHelper.arrs;

import org.junit.Assert;
import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.array.IntegerArrayConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:43:10
 */
public class IntegerArrayConverterTest {

    @Test
    public void toConvert() {
        IntegerArrayConverter integerArrayConverter = (IntegerArrayConverter) ConverterManager.lookup(int[].class);

        Assert.assertArrayEquals(arri(173, 234), integerArrayConverter.toConvert("173, 234"));
        Assert.assertArrayEquals(arri(173), integerArrayConverter.toConvert(Double.valueOf(173)));
        Assert.assertArrayEquals(arri(1, 7, 3), integerArrayConverter.toConvert(arri(1, 7, 3)));
        Assert.assertArrayEquals(arri(1, 7, 3), integerArrayConverter.toConvert(arrl(1, 7, 3)));
        Assert.assertArrayEquals(arri(1, 7, 3), integerArrayConverter.toConvert(arrf(1, 7, 3)));
        Assert.assertArrayEquals(arri(1, 7, 3), integerArrayConverter.toConvert(arrd(1.1, 7.99, 3)));
        Assert.assertArrayEquals(arri(173, 1022), integerArrayConverter.toConvert(arrs("173", "1022")));
        Assert.assertArrayEquals(arri(173, 10), integerArrayConverter.toConvert(arro("173", Integer.valueOf(10))));

        Assert.assertArrayEquals(arri(111, 777, 333),
                integerArrayConverter.toConvert(arrs("111", "   777     ", "333")));
        Assert.assertArrayEquals(arri(111, 777, 333), integerArrayConverter.toConvert("111,  777,  333"));

    }

}
