/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static com.ouer.solar.convert.ConverterTestHelper.arrc;

import org.junit.Assert;
import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:17:48
 */
public class ClassArrayConverterTest {

    @Test
    public void toConvert() {
        @SuppressWarnings({ "unchecked" })
        TypeConverter<Class<?>[]> classArrayConverter =
                (TypeConverter<Class<?>[]>) ConverterManager.lookup(Class[].class);

        Assert.assertArrayEquals(arrc(String.class), classArrayConverter.toConvert(String.class));
        Assert.assertArrayEquals(arrc(String.class, Integer.class),
                classArrayConverter.toConvert(arrc(String.class, Integer.class)));
        Assert.assertArrayEquals(arrc(Integer.class), classArrayConverter.toConvert("java.lang.Integer"));
        Assert.assertArrayEquals(arrc(Integer.class, String.class),
                classArrayConverter.toConvert("java.lang.Integer,    java.lang.String"));

        Assert.assertArrayEquals(arrc(Integer.class, String.class),
                classArrayConverter.toConvert("java.lang.Integer\n\n  java.lang.String  \n\n#java.lang.Long"));
    }

    @Test
    public void testMoreClassArrayConverter() {
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\n\r", Class[].class));
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\r\n", Class[].class));
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\r\r", Class[].class));
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\r\r\r", Class[].class));
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\n\n\n", Class[].class));
        Assert.assertArrayEquals(arrc(String.class), ConverterManager.convertType("java.lang.String,\n", Class[].class));
        Assert.assertArrayEquals(arrc(String.class), ConverterManager.convertType("java.lang.String,\r", Class[].class));

        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("java.lang.String,\r\n\r", Class[].class));
        Assert.assertArrayEquals(arrc(String.class),
                ConverterManager.convertType("\r\njava.lang.String,\r\n", Class[].class));
    }

}