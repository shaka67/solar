/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import com.ouer.solar.convert.ConvertUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:21:29
 */
public class ConvertTest {

    @Test
    public void testAllConversions() {

        assertEquals(new BigDecimal("11.2"), ConvertUtil.toBigDecimal("11.2"));
        assertNull(ConvertUtil.toBigDecimal(null));
        assertEquals(new BigInteger("123456789"), ConvertUtil.toBigInteger("123456789"));
        assertEquals(Boolean.TRUE, ConvertUtil.toBoolean(("true")));
        assertEquals(true, ConvertUtil.toBooleanValue("true", false));
        assertEquals(false, ConvertUtil.toBooleanValue(null, false));
        assertEquals(true, ConvertUtil.toBooleanValue("true"));
        assertEquals(false, ConvertUtil.toBooleanValue(null));

        // assertNotNull(ConvertUtil.toCalendar((new DatetimeObject())));

        assertEquals(Character.valueOf('A'), ConvertUtil.toCharacter("A"));
        assertEquals('A', ConvertUtil.toCharValue("A", ' '));
        assertEquals('A', ConvertUtil.toCharValue("A"));

        assertEquals(Integer.class, ConvertUtil.toClass("java.lang.Integer"));

        assertEquals(Integer.class, ConvertUtil.toClassArray("java.lang.Integer")[0]);
        assertEquals(1, ConvertUtil.toClassArray("java.lang.Integer").length);

        // assertNotNull(Convert.toDate(new DatetimeObject()));

        assertEquals(173, ConvertUtil.toIntValue("173"));
        assertEquals(173, ConvertUtil.toLongValue("173"));
        assertEquals(173, ConvertUtil.toShortValue("173"));
        assertEquals(17, ConvertUtil.toByteValue("17"));

        assertEquals(1.0d, ConvertUtil.toDouble("1").doubleValue(), 0.005);
        assertEquals(1.0d, ConvertUtil.toDoubleValue("1", 0), 0.005);
        assertEquals(1.0d, ConvertUtil.toDoubleValue("1"), 0.005);

        assertEquals(1.0f, ConvertUtil.toFloat("1").floatValue(), 0.005);
        assertEquals(1.0f, ConvertUtil.toFloatValue("1", 0), 0.005);
        assertEquals(1.0f, ConvertUtil.toFloat("1").floatValue(), 0.005);
        assertEquals(1.0f, ConvertUtil.toFloatValue("1"), 0.005);

        assertEquals(12, ConvertUtil.toInteger("12").intValue());

        // assertNotNull(ConvertUtil.toDatetimeObject(new GregorianCalendar()));

        assertEquals(555, ConvertUtil.toLong("555").longValue());

        assertEquals(555, ConvertUtil.toShort("555").shortValue());
        assertEquals(55, ConvertUtil.toByte("55").byteValue());

        assertNotNull(ConvertUtil.toString("555"));
    }

    @Test
    public void testArrayConversion() {
        assertArrayEquals(new String[] { "555", "12" }, ConvertUtil.toStringArray("555,12"));
        assertArrayEquals(new String[] { "555", " 12" }, ConvertUtil.toStringArray("555, 12"));
        AssertArraysTestHelper.assertArrayEquals(new boolean[] { true, false, true },
                ConvertUtil.toBooleanArray("1, 0, true"));
        assertArrayEquals(new int[] { 1, 2, -3 }, ConvertUtil.toIntegerArray("1, 2, -3"));
        assertArrayEquals(new long[] { -12, 2 }, ConvertUtil.toLongArray("-12, 2"));
        assertArrayEquals(new float[] { 1.1f, 2.2f }, ConvertUtil.toFloatArray("1.1, 2.2"), 0.5f);
        assertArrayEquals(new double[] { 1.1, 2.2, -3.3 }, ConvertUtil.toDoubleArray("1.1, 2.2, -3.3"), 0.5);
        assertArrayEquals(new short[] { -1, 2 }, ConvertUtil.toShortArray("-1,2"));
        assertArrayEquals(new char[] { 'a', ',', 'A' }, ConvertUtil.toCharacterArray("a,A"));
    }

    @Test
    public void testDefaultConversion() {

        assertEquals(true, ConvertUtil.toBooleanValue(null, true));
        assertEquals((byte) 23, ConvertUtil.toByteValue(null, (byte) 23));
        assertEquals('A', ConvertUtil.toCharValue(null, 'A'));
        assertEquals(1.4d, ConvertUtil.toDoubleValue(null, 1.4d), 0.005);
        assertEquals(1.4f, ConvertUtil.toFloatValue(null, 1.4f), 0.005);
        assertEquals(23L, ConvertUtil.toLongValue(null, 23L));
        assertEquals(7, ConvertUtil.toIntValue(null, 7));
        assertEquals(7, ConvertUtil.toShortValue(null, (short) 7));

        BigDecimal defaultBigDecimal = new BigDecimal("1.1");
        assertEquals(defaultBigDecimal, ConvertUtil.toBigDecimal(null, defaultBigDecimal));

        BigInteger defaultBigInteger = new BigInteger("173");
        assertEquals(defaultBigInteger, ConvertUtil.toBigInteger(null, defaultBigInteger));

        String defaultString = "123qweasdzxc";
        assertEquals(defaultString, ConvertUtil.toString(null, defaultString));

        // DatetimeObject datetimeObject = new DatetimeObject(2010, 4, 20);
        // assertEquals(datetimeObject, Convert.toDatetimeObject(null,
        // datetimeObject));

        // Date defaultDate = datetimeObject.convertToDate();
        // assertEquals(defaultDate, Convert.toDate(null, defaultDate));

        // Calendar defaultCalendar = datetimeObject.convertToCalendar();
        // assertEquals(defaultCalendar, Convert.toCalendar(null,
        // defaultCalendar));

        assertEquals(Boolean.TRUE, ConvertUtil.toBoolean(null, Boolean.TRUE));
        assertEquals(Byte.valueOf((byte) 123), ConvertUtil.toByte(null, Byte.valueOf((byte) 123)));
        assertEquals(Character.valueOf('A'), ConvertUtil.toCharacter(null, Character.valueOf('A')));
        assertEquals(Double.valueOf(5), ConvertUtil.toDouble(null, Double.valueOf(5)));
        assertEquals(Float.valueOf(5), ConvertUtil.toFloat(null, Float.valueOf(5)));
        assertEquals(Long.valueOf(7), ConvertUtil.toLong(null, Long.valueOf(7)));
        assertEquals(Integer.valueOf(8), ConvertUtil.toInteger(null, Integer.valueOf(8)));
        assertEquals(Short.valueOf((short) 3), ConvertUtil.toShort(null, Short.valueOf((short) 3)));

    }

}
