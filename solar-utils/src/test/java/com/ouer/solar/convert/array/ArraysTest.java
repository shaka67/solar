/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.junit.Test;

import com.ouer.solar.convert.AssertArraysTestHelper;
import com.ouer.solar.convert.ConverterManager;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午1:42:25
 */
public class ArraysTest {

    public static final Integer[] INTEGERS = new Integer[] { 1, 2, 3 };
    public static final int[] INTS = new int[] { 1, 2, 3 };
    public static final Long[] LONGS = new Long[] { 1L, 2L, 3L };
    public static final long[] LONGS1 = new long[] { 1L, 2L, 3L };
    public static final Float[] FLOATS = new Float[] { 1.1f, 2.2f, 3.3f };
    public static final float[] FLOATS1 = new float[] { 1.1f, 2.2f, 3.3f };
    public static final Double[] DOUBLES = new Double[] { 1.1, 2.2, 3.3 };
    public static final double[] DOUBLES1 = new double[] { 1.1, 2.2, 3.3 };
    public static final Short[] SHORTS = new Short[] { 1, 2, 3 };
    public static final short[] SHORTS1 = new short[] { 1, 2, 3 };
    public static final Byte[] BYTES = new Byte[] { 1, 2, 3 };
    public static final byte[] BYTES1 = new byte[] { 1, 2, 3 };
    public static final Character[] CHARACTERS = new Character[] { 'a', 'b', 'c' };
    public static final char[] CHARS = new char[] { 'a', 'b', 'c' };
    public static final Boolean[] BOOLEANS = new Boolean[] { true, false, true };
    public static final boolean[] BOOLEANS1 = new boolean[] { true, false, true };

    @Test
    public void testArrayToIntConversion() {
        Integer[] objects = INTEGERS;
        Object result = ConverterManager.convertType(objects, int[].class);

        assertNotNull(result);
        assertArrayEquals(INTS, (int[]) result);
    }

    @Test
    public void testIntToArrayConversion() {
        int[] primitives = INTS;
        Object result = ConverterManager.convertType(primitives, Integer[].class);

        assertNotNull(result);
        assertArrayEquals(INTEGERS, (Integer[]) result);
    }

    @Test
    public void testArrayToLongConversion() {
        Long[] objects = LONGS;
        Object result = ConverterManager.convertType(objects, long[].class);

        assertNotNull(result);
        assertArrayEquals(LONGS1, (long[]) result);
    }

    @Test
    public void testLongToArrayConversion() {
        long[] primitives = LONGS1;
        Object result = ConverterManager.convertType(primitives, Long[].class);

        assertNotNull(result);
        assertArrayEquals(LONGS, (Long[]) result);
    }

    @Test
    public void testArrayToFloatConversion() {
        Float[] objects = FLOATS;
        Object result = ConverterManager.convertType(objects, float[].class);

        assertNotNull(result);
        assertArrayEquals(FLOATS1, (float[]) result, 0.005f);
    }

    @Test
    public void testFloatToArrayConversion() {
        float[] primitives = FLOATS1;
        Object result = ConverterManager.convertType(primitives, Float[].class);

        assertNotNull(result);
        assertArrayEquals(FLOATS, (Float[]) result);
    }

    @Test
    public void testArrayToDoubleConversion() {
        Double[] objects = DOUBLES;
        Object result = ConverterManager.convertType(objects, double[].class);

        assertNotNull(result);
        assertArrayEquals(DOUBLES1, (double[]) result, 0.005f);
    }

    @Test
    public void testDoubleToArrayConversion() {
        double[] primitives = DOUBLES1;
        Object result = ConverterManager.convertType(primitives, Double[].class);

        assertNotNull(result);
        assertArrayEquals(DOUBLES, (Double[]) result);
    }

    @Test
    public void testArrayToShortConversion() {
        Short[] objects = SHORTS;
        Object result = ConverterManager.convertType(objects, short[].class);

        assertNotNull(result);
        assertArrayEquals(SHORTS1, (short[]) result);
    }

    @Test
    public void testShortToArrayConversion() {
        short[] primitives = SHORTS1;
        Object result = ConverterManager.convertType(primitives, Short[].class);

        assertNotNull(result);
        assertArrayEquals(SHORTS, (Short[]) result);
    }

    @Test
    public void testArrayToByteConversion() {
        Byte[] objects = BYTES;
        Object result = ConverterManager.convertType(objects, byte[].class);

        assertNotNull(result);
        assertArrayEquals(BYTES1, (byte[]) result);
    }

    @Test
    public void testByteToArrayConversion() {
        byte[] primitives = BYTES1;
        Object result = ConverterManager.convertType(primitives, Byte[].class);

        assertNotNull(result);
        assertArrayEquals(BYTES, (Byte[]) result);
    }

    @Test
    public void testArrayToCharConversion() {
        Character[] objects = CHARACTERS;
        Object result = ConverterManager.convertType(objects, char[].class);

        assertNotNull(result);
        assertArrayEquals(CHARS, (char[]) result);
    }

    @Test
    public void testCharToArrayConversion() {
        char[] primitives = CHARS;
        Object result = ConverterManager.convertType(primitives, Character[].class);

        assertNotNull(result);
        assertArrayEquals(CHARACTERS, (Character[]) result);
    }

    @Test
    public void testArrayToBooleanConversion() {
        Boolean[] objects = BOOLEANS;
        Object result = ConverterManager.convertType(objects, boolean[].class);

        assertNotNull(result);
        AssertArraysTestHelper.assertArrayEquals(BOOLEANS1, (boolean[]) result);
    }

    @Test
    public void testBooleanToArrayConversion() {
        boolean[] primitives = BOOLEANS1;
        Object result = ConverterManager.convertType(primitives, Boolean[].class);

        assertNotNull(result);
        assertArrayEquals(BOOLEANS, (Boolean[]) result);
    }

    // ---------------------------------------------------------------- single
    // value

    @Test
    public void testArrayToIntConversionSingleValue() {
        Object result = ConverterManager.convertType(Integer.valueOf(173), int[].class);

        assertNotNull(result);
        assertArrayEquals(new int[] { 173 }, (int[]) result);
    }

    @Test
    public void testIntToArrayConversionSingleValue() {
        Object result = ConverterManager.convertType(173, Integer[].class);

        assertNotNull(result);
        assertArrayEquals(new Integer[] { 173 }, (Integer[]) result);
    }

    // ---------------------------------------------------------------- string
    // csv

    @Test
    public void testArrayToIntConversionCommaSeparated() {
        Object result = ConverterManager.convertType("1,2,3", int[].class);

        assertNotNull(result);
        assertArrayEquals(new int[] { 1, 2, 3 }, (int[]) result);
    }

    // ---------------------------------------------------------------- prim 2
    // prim

    @Test
    public void testIntToLongArray() {
        Object result = ConverterManager.convertType(new int[] { 1, 2, 3 }, long[].class);

        assertNotNull(result);
        assertArrayEquals(new long[] { 1, 2, 3 }, (long[]) result);
    }

    @Test
    public void testIntToLongArray2() {
        Object result = ConverterManager.convertType(new int[][] { { 1, 2, 3 }, { 4, 5 } }, long[][].class);

        assertNotNull(result);
        AssertArraysTestHelper.assertArrayEquals(new long[][] { { 1, 2, 3 }, { 4, 5 } }, (long[][]) result);
    }

    // @Test
    // public void testIntToLongArray3() {
    // Object result = ConverterManager.convertType(new int[][] { { 1, 2, 3 },
    // { 4, 5 } }, Long[][].class);
    //
    // assertNotNull(result);
    // AssertArraysTestHelper.assertArrayEquals(new Long[][] { { 1l, 2l, 3l },
    // { 4l, 5l } }, (Long[][]) result);
    // }

    // ---------------------------------------------------------------- mutables

    // ---------------------------------------------------------------- bigint

    @Test
    public void testBigIntegerToInteger() {
        Object bigIntegers = new BigInteger[] { new BigInteger("7"), new BigInteger("3"), new BigInteger("1") };

        Object result = ConverterManager.convertType(bigIntegers, Integer[].class);
        assertNotNull(result);

        assertArrayEquals(new Integer[] { 7, 3, 1 }, (Integer[]) result);
    }

    @Test
    public void testIntegerToBigInteger() {
        Object integers = new Integer[] { new Integer(7), new Integer(3), new Integer(1) };

        Object result = ConverterManager.convertType(integers, BigInteger[].class);
        assertNotNull(result);

        BigInteger[] bigIntegers = (BigInteger[]) result;
        assertEquals(7, bigIntegers[0].intValue());
        assertEquals(3, bigIntegers[1].intValue());
        assertEquals(1, bigIntegers[2].intValue());
    }

    @Test
    public void testBigIntegerToInt() {
        Object bigIntegers = new BigInteger[] { new BigInteger("7"), new BigInteger("3"), new BigInteger("1") };

        Object result = ConverterManager.convertType(bigIntegers, int[].class);
        assertNotNull(result);

        assertArrayEquals(new int[] { 7, 3, 1 }, (int[]) result);
    }

    // @Test
    // public void testIntToBigInteger() {
    // int[] array = new int[] { 7, 3, 1 };
    //
    // Object result = ConverterManager.convertType(array, BigInteger[].class);
    // assertNotNull(result);
    //
    // BigInteger[] bigIntegers = (BigInteger[]) result;
    // assertEquals(7, bigIntegers[0].byteValue());
    // assertEquals(3, bigIntegers[1].byteValue());
    // assertEquals(1, bigIntegers[2].byteValue());
    // }

}
