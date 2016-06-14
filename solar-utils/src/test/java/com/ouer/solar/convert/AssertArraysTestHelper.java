/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import org.junit.Assert;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午1:43:00
 */
public final class AssertArraysTestHelper {

    public static void assertArrayEquals(Object[][] expected, Object[][] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i]);
        }
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }

    public static void assertArrayEquals(long[][] expected, long[][] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i]);
        }
    }

    public static boolean[] arrl(boolean...v) {
        return v;
    }

    public static Object[] arro(Object...v) {
        return v;
    }

    public static String[] arrs(String...v) {
        return v;
    }

    public static Class<?>[] arrc(Class<?>...v) {
        return v;
    }

    public static int[] arri(int...v) {
        return v;
    }

    public static long[] arrl(long...v) {
        return v;
    }

    public static byte[] arrb(byte...v) {
        return v;
    }

    public static short[] arrs(short...v) {
        return v;
    }

    public static char[] arrc(char...v) {
        return v;
    }

    public static double[] arrd(double...v) {
        return v;
    }

    public static float[] arrf(float...v) {
        return v;
    }

}
