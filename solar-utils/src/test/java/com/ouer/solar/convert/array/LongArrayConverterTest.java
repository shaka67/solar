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
import com.ouer.solar.convert.array.LongArrayConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:45:44
 */
public class LongArrayConverterTest {

    @Test
    public void toConvert() {
        LongArrayConverter longArrayConverter = (LongArrayConverter) ConverterManager.lookup(long[].class);

        Assert.assertArrayEquals(arrl(173), longArrayConverter.toConvert(Double.valueOf(173)));
        Assert.assertArrayEquals(arrl(173, 1022, 29929), longArrayConverter.toConvert(arrf(173, 1022, 29929)));
        Assert.assertArrayEquals(arrl(173, 1022, 29929), longArrayConverter.toConvert(arrd(173, 1022, 29929)));
        Assert.assertArrayEquals(arrl(173, 1022, 29929), longArrayConverter.toConvert(arri(173, 1022, 29929)));
        Assert.assertArrayEquals(arrl(173, 1022, 29929), longArrayConverter.toConvert(arrl(173, 1022, 29929)));
        Assert.assertArrayEquals(arrl(173, 1022), longArrayConverter.toConvert(arrs("173", "1022")));
        Assert.assertArrayEquals(arrl(173, 1022), longArrayConverter.toConvert(arro("173", Long.valueOf(1022))));

        Assert.assertArrayEquals(arrl(111, 777, 333), longArrayConverter.toConvert(arrs("111", "   777     ", "333")));
        Assert.assertArrayEquals(arrl(111, 777, 333), longArrayConverter.toConvert("111,  777,  333"));
    }

}