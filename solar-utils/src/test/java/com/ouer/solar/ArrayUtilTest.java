/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static com.ouer.solar.CollectionUtil.createArrayList;
import static com.ouer.solar.CollectionUtil.createLinkedHashMap;
import static com.ouer.solar.test.TestUtil.exception;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.ouer.solar.ArrayUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-12 下午7:16:34
 */
public class ArrayUtilTest {

    // ==========================================================================
    // 取得数组长度。
    // ==========================================================================

    @Test
    public void length() {
        assertEquals(0, ArrayUtil.length(null));

        assertEquals(0, ArrayUtil.length(new String[0]));
        assertEquals(10, ArrayUtil.length(new String[10]));

        assertEquals(0, ArrayUtil.length(new int[0]));
        assertEquals(10, ArrayUtil.length(new int[10]));

        assertEquals(0, ArrayUtil.length(new long[0]));
        assertEquals(10, ArrayUtil.length(new long[10]));

        assertEquals(0, ArrayUtil.length(new short[0]));
        assertEquals(10, ArrayUtil.length(new short[10]));

        assertEquals(0, ArrayUtil.length(new byte[0]));
        assertEquals(10, ArrayUtil.length(new byte[10]));

        assertEquals(0, ArrayUtil.length(new double[0]));
        assertEquals(10, ArrayUtil.length(new double[10]));

        assertEquals(0, ArrayUtil.length(new float[0]));
        assertEquals(10, ArrayUtil.length(new float[10]));

        assertEquals(0, ArrayUtil.length(new char[0]));
        assertEquals(10, ArrayUtil.length(new char[10]));

        assertEquals(0, ArrayUtil.length(new boolean[0]));
        assertEquals(10, ArrayUtil.length(new boolean[10]));

        assertEquals(0, ArrayUtil.length(new Object())); // not an array
    }

    // ==========================================================================
    // 判空函数。
    //
    // 判断一个数组是否为null或包含0个元素。
    // ==========================================================================

    @Test
    public void isEmpty() {
        assertTrue(ArrayUtil.isEmpty(null));

        assertTrue(ArrayUtil.isEmpty(new String[0]));
        assertFalse(ArrayUtil.isEmpty(new String[10]));

        assertTrue(ArrayUtil.isEmpty(new int[0]));
        assertFalse(ArrayUtil.isEmpty(new int[10]));

        assertFalse(ArrayUtil.isEmpty(new Object())); // not an array
    }

    // ==========================================================================
    // 默认值函数。
    //
    // 当数组为空时，取得默认数组值。
    // 注：判断数组为null时，可用更通用的ObjectUtil.defaultIfNull。
    // ==========================================================================

    @Test
    public void defaultIfEmpty() {
        // Object array
        String[] empty = new String[0];
        String[] nonempty = new String[10];

        assertSame(empty, ArrayUtil.defaultIfEmpty(null, empty));
        assertSame(empty, ArrayUtil.defaultIfEmpty(new String[0], empty));
        assertSame(nonempty, ArrayUtil.defaultIfEmpty(nonempty, empty));

        // primitive array
        long[] emptylong = new long[0];
        long[] nonemptylong = new long[10];

        assertSame(emptylong, ArrayUtil.defaultIfEmpty(null, emptylong));
        assertSame(emptylong, ArrayUtil.defaultIfEmpty(new long[0], emptylong));
        assertSame(nonemptylong, ArrayUtil.defaultIfEmpty(nonemptylong, emptylong));

        // non-array
        Object anyObject = new Object();

        assertSame(anyObject, ArrayUtil.defaultIfEmpty(anyObject, empty));
    }

    // ==========================================================================
    // 将数组转换成集合类。
    // ==========================================================================

    @Test
    public void arrayAsIterable() {
        // null array
        assertIterable(ArrayUtil.arrayAsIterable(String.class, null));

        // object array
        assertIterable(ArrayUtil.arrayAsIterable(String.class, new String[] { "a", "b", "c" }), "a", "b", "c");

        // primitive array
        assertIterable(ArrayUtil.arrayAsIterable(Integer.class, new int[] { 1, 2, 3 }), 1, 2, 3);
        assertIterable(ArrayUtil.arrayAsIterable(Long.class, new long[] { 1, 2, 3 }), 1L, 2L, 3L);
        assertIterable(ArrayUtil.arrayAsIterable(Short.class, new short[] { 1, 2, 3 }), (short) 1, (short) 2, (short) 3);
        assertIterable(ArrayUtil.arrayAsIterable(Byte.class, new byte[] { 1, 2, 3 }), (byte) 1, (byte) 2, (byte) 3);
        assertIterable(ArrayUtil.arrayAsIterable(Double.class, new double[] { 1, 2, 3 }), 1D, 2D, 3D);
        assertIterable(ArrayUtil.arrayAsIterable(Float.class, new float[] { 1, 2, 3 }), 1F, 2F, 3F);
        assertIterable(ArrayUtil.arrayAsIterable(Boolean.class, new boolean[] { true, false, true }), true, false, true);
        assertIterable(ArrayUtil.arrayAsIterable(Character.class, "abc".toCharArray()), 'a', 'b', 'c');

        // not an array
        try {
            ArrayUtil.arrayAsIterable(String.class, "a");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("a is not an array"));
        }

        // no componentType
        try {
            ArrayUtil.arrayAsIterable(null, new String[] { "a", "b", "c" });
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("componentType"));
        }

        // componentType not match
        try {
            ArrayUtil.arrayAsIterable(String.class, new int[] { 1, 2, 3 }).iterator().next();
            fail();
        } catch (ClassCastException e) {
        }
    }

    private <T> void assertIterable(Iterable<T> iterable, T...expected) {
        assertNotNull(iterable.iterator());

        // 检查iterable内容
        assertArrayEquals(expected, createArrayList(iterable).toArray());

        // 异常情况
        Iterator<T> i = iterable.iterator();

        try {
            i.remove();
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(e, exception("remove"));
        }

        for (@SuppressWarnings("unused")
        T e : expected) {
            i.next();
        }

        assertFalse(i.hasNext());

        try {
            i.next();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertThat(e, exception(expected.length + ""));
        }
    }

    @Test
    public void toMap() {
        // keyType is null
        try {
            ArrayUtil.toMap(null, null, null, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("keyType"));
        }

        // valueType is null
        try {
            ArrayUtil.toMap(null, String.class, null, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("valueType"));
        }

        Map<String, Integer> map = createLinkedHashMap();

        // keyValueArray is null
        assertNull(ArrayUtil.toMap(null, String.class, Integer.class)); // no map
        assertNull(ArrayUtil.toMap(null, String.class, Integer.class, null)); // no map
        assertSame(map, ArrayUtil.toMap(null, String.class, Integer.class, map)); // with map

        // keyValueArray is not null
        Object[][] colors = { { "RED", 0xFF0000 }, { "GREEN", 0x00FF00 }, { "BLUE", 0x0000FF } };

        Map<String, Integer> result = ArrayUtil.toMap(colors, String.class, Integer.class); // no map

        assertTrue(result instanceof LinkedHashMap<?, ?>);
        assertNotSame(map, result);
        assertEquals("{RED=16711680, GREEN=65280, BLUE=255}", result.toString());

        result = ArrayUtil.toMap(colors, String.class, Integer.class, null); // no map

        assertTrue(result instanceof LinkedHashMap<?, ?>);
        assertNotSame(map, result);
        assertEquals("{RED=16711680, GREEN=65280, BLUE=255}", result.toString());

        result = ArrayUtil.toMap(colors, String.class, Integer.class, map); // with map
        assertSame(map, result);
        assertEquals("{RED=16711680, GREEN=65280, BLUE=255}", result.toString());

        // keyType/valueType doesn't match
        try {
            ArrayUtil.toMap(colors, Long.class, Integer.class, null);
            fail();
        } catch (ClassCastException e) {
        }

        try {
            ArrayUtil.toMap(colors, String.class, Long.class, null);
            fail();
        } catch (ClassCastException e) {
        }

        // keyValue is null
        try {
            ArrayUtil.toMap(new Object[][] { { "RED", 0xFF0000, null }, null, { "BLUE", 0x0000FF } }, String.class,
                    Integer.class, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("Array element 1 is not an array of 2 elements"));
        }

        // keyValue is not object[2]
        try {
            ArrayUtil.toMap(new Object[][] { { "RED", 0xFF0000, null }, { "GREEN", 0x00FF00 }, { "BLUE" } },
                    String.class, Integer.class, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("Array element 2 is not an array of 2 elements"));
        }
    }

    // ==========================================================================
    // 比较数组的长度和类型。
    // ==========================================================================
    @Test
    public void isSameLength() {
        assertTrue(ArrayUtil.isSameLength(null, (String[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (long[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (int[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (short[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (byte[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (double[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (float[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (boolean[]) null));
        assertTrue(ArrayUtil.isSameLength(null, (char[]) null));

        assertFalse(ArrayUtil.isSameLength(null, new String[3]));
        assertFalse(ArrayUtil.isSameLength(null, new long[3]));
        assertFalse(ArrayUtil.isSameLength(null, new int[3]));
        assertFalse(ArrayUtil.isSameLength(null, new short[3]));
        assertFalse(ArrayUtil.isSameLength(null, new byte[3]));
        assertFalse(ArrayUtil.isSameLength(null, new double[3]));
        assertFalse(ArrayUtil.isSameLength(null, new float[3]));
        assertFalse(ArrayUtil.isSameLength(null, new boolean[3]));
        assertFalse(ArrayUtil.isSameLength(null, new char[3]));

        assertTrue(ArrayUtil.isSameLength(null, new String[0]));
        assertTrue(ArrayUtil.isSameLength(null, new long[0]));
        assertTrue(ArrayUtil.isSameLength(null, new int[0]));
        assertTrue(ArrayUtil.isSameLength(null, new short[0]));
        assertTrue(ArrayUtil.isSameLength(null, new byte[0]));
        assertTrue(ArrayUtil.isSameLength(null, new double[0]));
        assertTrue(ArrayUtil.isSameLength(null, new float[0]));
        assertTrue(ArrayUtil.isSameLength(null, new boolean[0]));
        assertTrue(ArrayUtil.isSameLength(null, new char[0]));

        assertFalse(ArrayUtil.isSameLength(new String[3], null));
        assertFalse(ArrayUtil.isSameLength(new long[3], null));
        assertFalse(ArrayUtil.isSameLength(new int[3], null));
        assertFalse(ArrayUtil.isSameLength(new short[3], null));
        assertFalse(ArrayUtil.isSameLength(new byte[3], null));
        assertFalse(ArrayUtil.isSameLength(new double[3], null));
        assertFalse(ArrayUtil.isSameLength(new float[3], null));
        assertFalse(ArrayUtil.isSameLength(new boolean[3], null));
        assertFalse(ArrayUtil.isSameLength(new char[3], null));

        assertTrue(ArrayUtil.isSameLength(new String[3], new String[3]));
        assertTrue(ArrayUtil.isSameLength(new long[3], new long[3]));
        assertTrue(ArrayUtil.isSameLength(new int[3], new int[3]));
        assertTrue(ArrayUtil.isSameLength(new short[3], new short[3]));
        assertTrue(ArrayUtil.isSameLength(new byte[3], new byte[3]));
        assertTrue(ArrayUtil.isSameLength(new double[3], new double[3]));
        assertTrue(ArrayUtil.isSameLength(new float[3], new float[3]));
        assertTrue(ArrayUtil.isSameLength(new boolean[3], new boolean[3]));
        assertTrue(ArrayUtil.isSameLength(new char[3], new char[3]));

        assertFalse(ArrayUtil.isSameLength(new String[3], new String[4]));
        assertFalse(ArrayUtil.isSameLength(new long[3], new long[4]));
        assertFalse(ArrayUtil.isSameLength(new int[3], new int[4]));
        assertFalse(ArrayUtil.isSameLength(new short[3], new short[4]));
        assertFalse(ArrayUtil.isSameLength(new byte[3], new byte[4]));
        assertFalse(ArrayUtil.isSameLength(new double[3], new double[4]));
        assertFalse(ArrayUtil.isSameLength(new float[3], new float[4]));
        assertFalse(ArrayUtil.isSameLength(new boolean[3], new boolean[4]));
        assertFalse(ArrayUtil.isSameLength(new char[3], new char[4]));
    }

    // ==========================================================================
    // 反转数组的元素顺序。
    // ==========================================================================

    @Test
    public void reverse() {
        ArrayUtil.reverse((Object[]) null);
        ArrayUtil.reverse((long[]) null);
        ArrayUtil.reverse((short[]) null);
        ArrayUtil.reverse((int[]) null);
        ArrayUtil.reverse((double[]) null);
        ArrayUtil.reverse((float[]) null);
        ArrayUtil.reverse((boolean[]) null);
        ArrayUtil.reverse((char[]) null);
        ArrayUtil.reverse((byte[]) null);

        // object
        Object[] objectArray;

        objectArray = new Object[0];
        ArrayUtil.reverse(objectArray);
        assertArrayReverse(new Object[0], objectArray);

        objectArray = new Object[] { "aaa", "bbb", "ccc" };
        ArrayUtil.reverse(objectArray);
        assertArrayReverse(new Object[] { "ccc", "bbb", "aaa" }, objectArray);

        objectArray = new Object[] { "aaa", "bbb", "ccc", "ddd" };
        ArrayUtil.reverse(objectArray);
        assertArrayReverse(new Object[] { "ddd", "ccc", "bbb", "aaa" }, objectArray);

        // long
        long[] longArray;

        longArray = new long[0];
        ArrayUtil.reverse(longArray);
        assertArrayReverse(new long[0], longArray);

        longArray = new long[] { 111, 222, 333 };
        ArrayUtil.reverse(longArray);
        assertArrayReverse(new long[] { 333, 222, 111 }, longArray);

        longArray = new long[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(longArray);
        assertArrayReverse(new long[] { 444, 333, 222, 111 }, longArray);

        // int
        int[] intArray;

        intArray = new int[0];
        ArrayUtil.reverse(intArray);
        assertArrayReverse(new int[0], intArray);

        intArray = new int[] { 111, 222, 333 };
        ArrayUtil.reverse(intArray);
        assertArrayReverse(new int[] { 333, 222, 111 }, intArray);

        intArray = new int[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(intArray);
        assertArrayReverse(new int[] { 444, 333, 222, 111 }, intArray);

        // short
        short[] shortArray;

        shortArray = new short[0];
        ArrayUtil.reverse(shortArray);
        assertArrayReverse(new short[0], shortArray);

        shortArray = new short[] { 111, 222, 333 };
        ArrayUtil.reverse(shortArray);
        assertArrayReverse(new short[] { 333, 222, 111 }, shortArray);

        shortArray = new short[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(shortArray);
        assertArrayReverse(new short[] { 444, 333, 222, 111 }, shortArray);

        // byte
        byte[] byteArray;

        byteArray = new byte[0];
        ArrayUtil.reverse(byteArray);
        assertArrayReverse(new byte[0], byteArray);

        byteArray = new byte[] { 111, (byte) 222, (byte) 333 };
        ArrayUtil.reverse(byteArray);
        assertArrayReverse(new byte[] { (byte) 333, (byte) 222, 111 }, byteArray);

        byteArray = new byte[] { 111, (byte) 222, (byte) 333, (byte) 444 };
        ArrayUtil.reverse(byteArray);
        assertArrayReverse(new byte[] { (byte) 444, (byte) 333, (byte) 222, 111 }, byteArray);

        // double
        double[] doubleArray;

        doubleArray = new double[0];
        ArrayUtil.reverse(doubleArray);
        assertArrayReverse(new double[0], doubleArray);

        doubleArray = new double[] { 111, 222, 333 };
        ArrayUtil.reverse(doubleArray);
        assertArrayReverse(new double[] { 333, 222, 111 }, doubleArray);

        doubleArray = new double[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(doubleArray);
        assertArrayReverse(new double[] { 444, 333, 222, 111 }, doubleArray);

        // float
        float[] floatArray;

        floatArray = new float[0];
        ArrayUtil.reverse(floatArray);
        assertArrayReverse(new float[0], floatArray);

        floatArray = new float[] { 111, 222, 333 };
        ArrayUtil.reverse(floatArray);
        assertArrayReverse(new float[] { 333, 222, 111 }, floatArray);

        floatArray = new float[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(floatArray);
        assertArrayReverse(new float[] { 444, 333, 222, 111 }, floatArray);

        // boolean
        boolean[] booleanArray;

        booleanArray = new boolean[0];
        ArrayUtil.reverse(booleanArray);
        assertArrayReverse(new boolean[0], booleanArray);

        booleanArray = new boolean[] { true, false, false };
        ArrayUtil.reverse(booleanArray);
        assertArrayReverse(new boolean[] { false, false, true }, booleanArray);

        booleanArray = new boolean[] { true, false, false, false };
        ArrayUtil.reverse(booleanArray);
        assertArrayReverse(new boolean[] { false, false, false, true }, booleanArray);

        // char
        char[] charArray;

        charArray = new char[0];
        ArrayUtil.reverse(charArray);
        assertArrayReverse(new char[0], charArray);

        charArray = new char[] { 111, 222, 333 };
        ArrayUtil.reverse(charArray);
        assertArrayReverse(new char[] { 333, 222, 111 }, charArray);

        charArray = new char[] { 111, 222, 333, 444 };
        ArrayUtil.reverse(charArray);
        assertArrayReverse(new char[] { 444, 333, 222, 111 }, charArray);
    }

    private void assertArrayReverse(Object expected, Object reversed) {
        assertEquals(Array.getLength(expected), Array.getLength(reversed));

        for (int i = 0; i < Array.getLength(expected); i++) {
            assertEquals(Array.get(expected, i), Array.get(reversed, i));
        }
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：Object[]
    // ==========================================================================
    @Test
    public void indexOfObject() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf(null, "a"));
        assertEquals(1, ArrayUtil.indexOf(new String[] { "a", null, "c" }, (String) null));
        assertEquals(-1, ArrayUtil.indexOf(new String[] { "a", "b", "c" }, (String) null));
        assertEquals(-1, ArrayUtil.indexOf(new String[0], "a"));
        assertEquals(0, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "a"));
        assertEquals(2, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b"));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf(null, "a", 0));
        assertEquals(1, ArrayUtil.indexOf(new String[] { "a", null, "c" }, (String) null, 0));
        assertEquals(-1, ArrayUtil.indexOf(new String[0], "a", 0));
        assertEquals(2, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 0));
        assertEquals(5, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 3));
        assertEquals(-1, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 9));
        assertEquals(2, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", -1));
    }

    @Test
    public void indexOfObjectArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf(null, new String[] { "a" }));
        assertEquals(-1, ArrayUtil.indexOf(new String[] { "a", "b", "c" }, null));
        assertEquals(0, ArrayUtil.indexOf(new String[0], new String[0]));
        assertEquals(0,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a" }));
        assertEquals(2,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }));
        assertEquals(1,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a", "b" }));
        assertEquals(0, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf(null, new String[] { "a" }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new String[] { "a", "b", "c" }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new String[0], new String[0], 0));
        assertEquals(0,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a" }, 0));
        assertEquals(2,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 0));
        assertEquals(1, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a",
                "b" }, 0));
        assertEquals(5,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 3));
        assertEquals(-1,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 9));
        assertEquals(2,
                ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, -1));
        assertEquals(2, ArrayUtil.indexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new String[] { "a", "b", "c" }, new String[0], 9));
    }

    @Test
    public void lastIndexOfObject() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf(null, "a"));
        assertEquals(1, ArrayUtil.lastIndexOf(new String[] { "a", null, "c" }, (String) null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "b", "c" }, (String) null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[0], "a"));
        assertEquals(7, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "a"));
        assertEquals(5, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b"));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf(null, "a", 0));
        assertEquals(1, ArrayUtil.lastIndexOf(new String[] { "a", null, "c" }, (String) null, 2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[0], "a", 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 8));
        assertEquals(2, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 4));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "b", -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, "a", 0));
    }

    @Test
    public void lastIndexOfObjectArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf(null, new String[] { "a" }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "b", "c" }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[0], new String[] { "a" }));
        assertEquals(7,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a" }));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }));
        assertEquals(5, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] {
                "b", "a" }));

        assertEquals(8, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf(null, new String[] { "a" }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "b", "c" }, null, 0));
        assertEquals(7,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a" }, 8));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] {
                "a", "b" }, 8));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" },
                new String[] { "b" }, -1));
        assertEquals(0,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "a" }, 0));
        assertEquals(-1,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] { "b" }, 0));

        assertEquals(0,
                ArrayUtil.lastIndexOf(new String[] { "a", "a", "b", "a", "a", "b", "a", "a" }, new String[] {}, 0));
    }

    @Test
    public void containsObject() {
        assertFalse(ArrayUtil.contains(null, "a"));
        assertTrue(ArrayUtil.contains(new String[] { "a", null, "c" }, (String) null));
        assertFalse(ArrayUtil.contains(new String[0], "a"));
        assertTrue(ArrayUtil.contains(new String[] { "a", "b", "c" }, "a"));
        assertFalse(ArrayUtil.contains(new String[] { "a", "b", "c" }, "z"));
    }

    @Test
    public void containsObjectArray() {
        assertFalse(ArrayUtil.contains(null, new String[] { "a" }));
        assertFalse(ArrayUtil.contains(new String[] { "a", "b", "c" }, null));
        assertTrue(ArrayUtil.contains(new String[0], new String[0]));
        assertTrue(ArrayUtil.contains(new String[] { "a", "b", "c" }, new String[0]));
        assertTrue(ArrayUtil.contains(new String[] { "a", "b", "c" }, new String[] { "a" }));
        assertFalse(ArrayUtil.contains(new String[] { "a", "b", "c" }, new String[] { "z" }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：long[]
    // ==========================================================================
    @Test
    public void indexOfLong() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((long[]) null, 1L));
        assertEquals(-1, ArrayUtil.indexOf(new long[0], 1L));
        assertEquals(0, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 1L));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((long[]) null, 1L, 0));
        assertEquals(-1, ArrayUtil.indexOf(new long[0], 1L, 0));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 0));
        assertEquals(5, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 3));
        assertEquals(-1, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 9));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, -1));
    }

    @Test
    public void indexOfLongArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((long[]) null, new long[] { 1L }));
        assertEquals(-1, ArrayUtil.indexOf(new long[] { 1L, 2L, 3L }, null));
        assertEquals(0, ArrayUtil.indexOf(new long[0], new long[0]));
        assertEquals(0, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L }));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }));
        assertEquals(1, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L, 2L }));
        assertEquals(0, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((long[]) null, new long[] { 1L }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new long[] { 1L, 2L, 3L }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new long[0], new long[0], 0));
        assertEquals(0, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L }, 0));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 0));
        assertEquals(1, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L, 2L }, 0));
        assertEquals(5, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 3));
        assertEquals(-1, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 9));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, -1));
        assertEquals(2, ArrayUtil.indexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new long[] { 1L, 2L, 3L }, new long[0], 9));
    }

    @Test
    public void lastIndexOfLong() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((long[]) null, 1L));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[0], 1L));
        assertEquals(7, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 1L));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((long[]) null, 1L, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[0], 1L, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 8));
        assertEquals(2, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 4));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 2L, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, 1L, 0));
    }

    @Test
    public void lastIndexOfLongArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((long[]) null, new long[] { 1L }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 2L, 3L }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[0], new long[] { 1L }));
        assertEquals(7, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L }));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L, 1L }));

        assertEquals(8, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((long[]) null, new long[] { 1L }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 2L, 3L }, null, 0));
        assertEquals(7, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L, 2L }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 1L }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] { 2L }, 0));

        assertEquals(0, ArrayUtil.lastIndexOf(new long[] { 1L, 1L, 2L, 1L, 1L, 2L, 1L, 1L }, new long[] {}, 0));
    }

    @Test
    public void containsLong() {
        assertFalse(ArrayUtil.contains((long[]) null, 1L));
        assertFalse(ArrayUtil.contains(new long[0], 1L));
        assertTrue(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, 1L));
        assertFalse(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, 26L));
    }

    @Test
    public void containsLongArray() {
        assertFalse(ArrayUtil.contains((long[]) null, new long[] { 1L }));
        assertFalse(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, null));
        assertTrue(ArrayUtil.contains(new long[0], new long[0]));
        assertTrue(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, new long[0]));
        assertTrue(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, new long[] { 1L }));
        assertFalse(ArrayUtil.contains(new long[] { 1L, 2L, 3L }, new long[] { 26L }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：int[]
    // ==========================================================================
    @Test
    public void indexOfInt() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((int[]) null, 1));
        assertEquals(-1, ArrayUtil.indexOf(new int[0], 1));
        assertEquals(0, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((int[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new int[0], 1, 0));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 3));
        assertEquals(-1, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
    }

    @Test
    public void indexOfIntArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((int[]) null, new int[] { 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new int[] { 1, 2, 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new int[0], new int[0]));
        assertEquals(0, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1 }));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }));
        assertEquals(1, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1, 2 }));
        assertEquals(0, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((int[]) null, new int[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new int[] { 1, 2, 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new int[0], new int[0], 0));
        assertEquals(0, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1 }, 0));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 0));
        assertEquals(1, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1, 2 }, 0));
        assertEquals(5, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 3));
        assertEquals(-1, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 9));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, -1));
        assertEquals(2, ArrayUtil.indexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new int[] { 1, 2, 3 }, new int[0], 9));
    }

    @Test
    public void lastIndexOfInt() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((int[]) null, 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[0], 1));
        assertEquals(7, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((int[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[0], 1, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 8));
        assertEquals(2, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 4));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1, 0));
    }

    @Test
    public void lastIndexOfIntArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((int[]) null, new int[] { 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 2, 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[0], new int[] { 1 }));
        assertEquals(7, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2, 1 }));

        assertEquals(8, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((int[]) null, new int[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 2, 3 }, null, 0));
        assertEquals(7, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1, 2 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] { 2 }, 0));

        assertEquals(0, ArrayUtil.lastIndexOf(new int[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new int[] {}, 0));
    }

    @Test
    public void containsInt() {
        assertFalse(ArrayUtil.contains((int[]) null, 1));
        assertFalse(ArrayUtil.contains(new int[0], 1));
        assertTrue(ArrayUtil.contains(new int[] { 1, 2, 3 }, 1));
        assertFalse(ArrayUtil.contains(new int[] { 1, 2, 3 }, 26));
    }

    @Test
    public void containsIntArray() {
        assertFalse(ArrayUtil.contains((int[]) null, new int[] { 1 }));
        assertFalse(ArrayUtil.contains(new int[] { 1, 2, 3 }, null));
        assertTrue(ArrayUtil.contains(new int[0], new int[0]));
        assertTrue(ArrayUtil.contains(new int[] { 1, 2, 3 }, new int[0]));
        assertTrue(ArrayUtil.contains(new int[] { 1, 2, 3 }, new int[] { 1 }));
        assertFalse(ArrayUtil.contains(new int[] { 1, 2, 3 }, new int[] { 26 }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：short[]
    // ==========================================================================
    @Test
    public void indexOfShort() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((short[]) null, (short) 1));
        assertEquals(-1, ArrayUtil.indexOf(new short[0], (short) 1));
        assertEquals(
                0,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((short[]) null, (short) 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new short[0], (short) 1, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, -1));
    }

    @Test
    public void indexOfShortArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((short[]) null, new short[] { (short) 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new short[] { (short) 1, (short) 2, (short) 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new short[0], new short[0]));
        assertEquals(
                0,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1 }));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }));
        assertEquals(
                1,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1, (short) 2 }));
        assertEquals(
                0,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((short[]) null, new short[] { (short) 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new short[] { (short) 1, (short) 2, (short) 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new short[0], new short[0], 0));
        assertEquals(
                0,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1 }, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 0));
        assertEquals(
                1,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1, (short) 2 }, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, -1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new short[] { (short) 1, (short) 2, (short) 3 }, new short[0], 9));
    }

    @Test
    public void lastIndexOfShort() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((short[]) null, (short) 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new short[0], (short) 1));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 1));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((short[]) null, (short) 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new short[0], (short) 1, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 8));
        assertEquals(
                2,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 4));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 2, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, (short) 1, 0));
    }

    @Test
    public void lastIndexOfShortArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((short[]) null, new short[] { (short) 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 2, (short) 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new short[0], new short[] { (short) 1 }));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2, (short) 1 }));

        assertEquals(
                8,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((short[]) null, new short[] { (short) 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 2, (short) 3 }, null, 0));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 8));
        assertEquals(
                4,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1, (short) 2 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 1 }, 0));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] { (short) 2 }, 0));

        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new short[] { (short) 1, (short) 1, (short) 2, (short) 1, (short) 1, (short) 2,
                        (short) 1, (short) 1 }, new short[] {}, 0));
    }

    @Test
    public void containsShort() {
        assertFalse(ArrayUtil.contains((short[]) null, (short) 1));
        assertFalse(ArrayUtil.contains(new short[0], (short) 1));
        assertTrue(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, (short) 1));
        assertFalse(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, (short) 26));
    }

    @Test
    public void containsShortArray() {
        assertFalse(ArrayUtil.contains((short[]) null, new short[] { (short) 1 }));
        assertFalse(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, null));
        assertTrue(ArrayUtil.contains(new short[0], new short[0]));
        assertTrue(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, new short[0]));
        assertTrue(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, new short[] { (short) 1 }));
        assertFalse(ArrayUtil.contains(new short[] { (short) 1, (short) 2, (short) 3 }, new short[] { (short) 26 }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：byte[]
    // ==========================================================================
    @Test
    public void indexOfByte() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((byte[]) null, (byte) 1));
        assertEquals(-1, ArrayUtil.indexOf(new byte[0], (byte) 1));
        assertEquals(
                0,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((byte[]) null, (byte) 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new byte[0], (byte) 1, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 2, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 2, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 2, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, (byte) 2, -1));
    }

    @Test
    public void indexOfByteArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((byte[]) null, new byte[] { (byte) 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new byte[0], new byte[0]));
        assertEquals(
                0,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 1 }));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 2 }));
        assertEquals(
                1,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 1, (byte) 2 }));
        assertEquals(
                0,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((byte[]) null, new byte[] { (byte) 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new byte[0], new byte[0], 0));
        assertEquals(
                0,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 1 }, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 2 }, 0));
        assertEquals(
                1,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 1, (byte) 2 }, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 2 }, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 2 }, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[] { (byte) 2 }, -1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2, (byte) 1,
                        (byte) 1 }, new byte[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, new byte[0], 9));
    }

    @Test
    public void lastIndexOfByte() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((byte[]) null, (byte) 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new byte[0], (byte) 1));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 1));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((byte[]) null, (byte) 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new byte[0], (byte) 1, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2, 8));
        assertEquals(
                2,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2, 4));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 2, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, (byte) 1, 0));
    }

    @Test
    public void lastIndexOfByteArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((byte[]) null, new byte[] { (byte) 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new byte[0], new byte[] { (byte) 1 }));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 1 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2, (byte) 1 }));

        assertEquals(
                8,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((byte[]) null, new byte[] { (byte) 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, null, 0));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 1 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2 }, 8));
        assertEquals(
                4,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 1, (byte) 2 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2 }, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2 }, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 1 }, 0));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] { (byte) 2 }, 0));

        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2,
                        (byte) 1, (byte) 1 }, new byte[] {}, 0));
    }

    @Test
    public void containsByte() {
        assertFalse(ArrayUtil.contains((byte[]) null, (byte) 1));
        assertFalse(ArrayUtil.contains(new byte[0], (byte) 1));
        assertTrue(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 1));
        assertFalse(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 26));
    }

    @Test
    public void containsByteArray() {
        assertFalse(ArrayUtil.contains((byte[]) null, new byte[] { (byte) 1 }));
        assertFalse(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, null));
        assertTrue(ArrayUtil.contains(new byte[0], new byte[0]));
        assertTrue(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, new byte[0]));
        assertTrue(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, new byte[] { (byte) 1 }));
        assertFalse(ArrayUtil.contains(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, new byte[] { (byte) 26 }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：double[]
    // ==========================================================================
    @Test
    public void indexOfDouble() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, 1));
        assertEquals(-1, ArrayUtil.indexOf(new double[0], 1));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new double[0], 1, 0));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 3));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
    }

    @Test
    public void indexOfDoubleTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, 1, 0.2));
        assertEquals(-1, ArrayUtil.indexOf(new double[0], 1, 0.2));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 1, 0.2));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 0.2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, 1, 0, 0.2));
        assertEquals(-1, ArrayUtil.indexOf(new double[0], 1, 0, 0.2));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 0, 0.2));
        assertEquals(5, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 3, 0.2));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 9, 0.2));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, -1, 0.2));
    }

    @Test
    public void indexOfDoubleArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, new double[] { 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1, 2, 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new double[0], new double[0]));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1 }));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }));
        assertEquals(1, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1, 2 }));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, new double[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1, 2, 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new double[0], new double[0], 0));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1 }, 0));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 0));
        assertEquals(1, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1, 2 }, 0));
        assertEquals(5, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 3));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 9));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, -1));
        assertEquals(2, ArrayUtil.indexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new double[] { 1, 2, 3 }, new double[0], 9));
    }

    @Test
    public void indexOfDoubleArrayTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, new double[] { 1.1 }, 0.2));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1.1, 2.1, 3.1 }, null, 0.2));
        assertEquals(0, ArrayUtil.indexOf(new double[0], new double[0], 0.2));
        assertEquals(0,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 1 }, 0.2));
        assertEquals(2,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, 0.2));
        assertEquals(1,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 1, 2 }, 0.2));
        assertEquals(0, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[0], 0.2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((double[]) null, new double[] { 1 }, 0, 0.2));
        assertEquals(-1, ArrayUtil.indexOf(new double[] { 1, 2, 3 }, null, 0, 0.2));
        assertEquals(0, ArrayUtil.indexOf(new double[0], new double[0], 0, 0.2));
        assertEquals(0,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 1 }, 0, 0.2));
        assertEquals(2,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, 0, 0.2));
        assertEquals(1, ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 1, 2 }, 0, 0.2));
        assertEquals(5,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, 3, 0.2));
        assertEquals(-1,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, 9, 0.2));
        assertEquals(2,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, -1, 0.2));
        assertEquals(2,
                ArrayUtil.indexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[0], 2, 0.2));
        assertEquals(3, ArrayUtil.indexOf(new double[] { 1.1, 2.1, 3.1 }, new double[0], 9, 0.2));
    }

    @Test
    public void lastIndexOfDouble() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], 1));
        assertEquals(7, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], 1, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 8));
        assertEquals(2, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 4));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1, 0));
    }

    @Test
    public void lastIndexOfDoubleTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, 1, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], 1, 0.2));
        assertEquals(7, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 1, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 0.2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, 1, 0, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], 1, 0, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 8, 0.2));
        assertEquals(2, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 4, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 0, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, 9, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 2, -1, 0.2));
        assertEquals(0, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, 1, 0, 0.2));
    }

    @Test
    public void lastIndexOfDoubleArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, new double[] { 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 2, 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], new double[] { 1 }));
        assertEquals(7, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2, 1 }));

        assertEquals(8, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, new double[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 2, 3 }, null, 0));
        assertEquals(7, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1, 2 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] { 2 }, 0));

        assertEquals(0, ArrayUtil.lastIndexOf(new double[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new double[] {}, 0));
    }

    @Test
    public void lastIndexOfDoubleArrayTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, new double[] { 1 }, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 2.1, 3.1 }, null, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[0], new double[] { 1 }, 0.2));
        assertEquals(7,
                ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 1 }, 0.2));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] { 2 }, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] {
                2, 1 }, 0.2));

        assertEquals(8,
                ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] {}, 0.2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((double[]) null, new double[] { 1 }, 0, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 2.1, 3.1 }, null, 0, 0.2));
        assertEquals(7, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 1 }, 8, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 2 }, 8, 0.2));
        assertEquals(4, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] {
                1, 2 }, 8, 0.2));
        assertEquals(5, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 2 }, 9, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 2 }, -1, 0.2));
        assertEquals(0, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 1 }, 0, 0.2));
        assertEquals(-1, ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 },
                new double[] { 2 }, 0, 0.2));

        assertEquals(0,
                ArrayUtil.lastIndexOf(new double[] { 1.1, 1.1, 2.1, 1.1, 1.1, 2.1, 1.1, 1.1 }, new double[] {}, 0, 0.2));
    }

    @Test
    public void containsDouble() {
        assertFalse(ArrayUtil.contains((double[]) null, 1));
        assertFalse(ArrayUtil.contains(new double[0], 1));
        assertTrue(ArrayUtil.contains(new double[] { 1, 2, 3 }, 1));
        assertFalse(ArrayUtil.contains(new double[] { 1, 2, 3 }, 26));
    }

    @Test
    public void containsDoubleTolerance() {
        assertFalse(ArrayUtil.contains((double[]) null, 1, 0.2));
        assertFalse(ArrayUtil.contains(new double[0], 1, 0.2));
        assertTrue(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, 1, 0.2));
        assertFalse(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, 26, 0.2));
    }

    @Test
    public void containsDoubleArray() {
        assertFalse(ArrayUtil.contains((double[]) null, new double[] { 1 }));
        assertFalse(ArrayUtil.contains(new double[] { 1, 2, 3 }, null));
        assertTrue(ArrayUtil.contains(new double[0], new double[0]));
        assertTrue(ArrayUtil.contains(new double[] { 1, 2, 3 }, new double[0]));
        assertTrue(ArrayUtil.contains(new double[] { 1, 2, 3 }, new double[] { 1 }));
        assertFalse(ArrayUtil.contains(new double[] { 1, 2, 3 }, new double[] { 26 }));
    }

    @Test
    public void containsDoubleArrayTolerance() {
        assertFalse(ArrayUtil.contains((double[]) null, new double[] { 1 }, 0.2));
        assertFalse(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, null, 0.2));
        assertTrue(ArrayUtil.contains(new double[0], new double[0], 0.2));
        assertTrue(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, new double[0], 0.2));
        assertTrue(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, new double[] { 1 }, 0.2));
        assertFalse(ArrayUtil.contains(new double[] { 1.1, 2.1, 3.1 }, new double[] { 26 }, 0.2));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：float[]
    // ==========================================================================
    @Test
    public void indexOfFloat() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, 1));
        assertEquals(-1, ArrayUtil.indexOf(new float[0], 1));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new float[0], 1, 0));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 3));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
    }

    @Test
    public void indexOfFloatTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, 1, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[0], 1, 0.2F));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 1, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 0.2F));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, 1, 0, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[0], 1, 0, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 0, 0.2F));
        assertEquals(5, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 3, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 9, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, -1, 0.2F));
    }

    @Test
    public void indexOfFloatArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, new float[] { 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1, 2, 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new float[0], new float[0]));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1 }));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }));
        assertEquals(1, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1, 2 }));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, new float[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1, 2, 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new float[0], new float[0], 0));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1 }, 0));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 0));
        assertEquals(1, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1, 2 }, 0));
        assertEquals(5, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 3));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 9));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, -1));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new float[] { 1, 2, 3 }, new float[0], 9));
    }

    @Test
    public void indexOfFloatArrayTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, new float[] { 1 }, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1.1F, 2.1F, 3.1F }, null, 0.2F));
        assertEquals(0, ArrayUtil.indexOf(new float[0], new float[0], 0.2F));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 1 }, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 0.2F));
        assertEquals(1, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[] {
                1, 2 }, 0.2F));
        assertEquals(0,
                ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[0], 0.2F));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((float[]) null, new float[] { 1 }, 0, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1.1F, 2.1F, 3.1F }, null, 0, 0.2F));
        assertEquals(0, ArrayUtil.indexOf(new float[0], new float[0], 0, 0.2F));
        assertEquals(0, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 1 }, 0, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 0, 0.2F));
        assertEquals(1, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[] {
                1, 2 }, 0, 0.2F));
        assertEquals(5, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 3, 0.2F));
        assertEquals(-1, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 9, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, -1, 0.2F));
        assertEquals(2, ArrayUtil.indexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[0],
                2, 0.2F));
        assertEquals(3, ArrayUtil.indexOf(new float[] { 1.1F, 2.1F, 3.1F }, new float[0], 9, 0.2F));
    }

    @Test
    public void lastIndexOfFloat() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], 1));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], 1, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 8));
        assertEquals(2, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 4));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 0));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 2, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, 1, 0));
    }

    @Test
    public void lastIndexOfFloatTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, 1, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], 1, 0.2F));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 1, 0.2F));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 0.2F));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, 1, 0, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], 1, 0, 0.2F));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 8, 0.2F));
        assertEquals(2,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 4, 0.2F));
        assertEquals(-1,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 0, 0.2F));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, 9, 0.2F));
        assertEquals(-1,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 2, -1, 0.2F));
        assertEquals(0,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, 1, 0, 0.2F));
    }

    @Test
    public void lastIndexOfFloatArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, new float[] { 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 2, 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], new float[] { 1 }));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2, 1 }));

        assertEquals(8, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, new float[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 2, 3 }, null, 0));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1, 2 }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] { 2 }, 0));

        assertEquals(0, ArrayUtil.lastIndexOf(new float[] { 1, 1, 2, 1, 1, 2, 1, 1 }, new float[] {}, 0));
    }

    @Test
    public void lastIndexOfFloatArrayTolerance() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, new float[] { 1 }, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1.1F, 2.1F, 3.1F }, null, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[0], new float[] { 1 }, 0.2F));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 1 }, 0.2F));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 0.2F));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[] { 2,
                        1 }, 0.2F));

        assertEquals(8, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] {}, 0.2F));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((float[]) null, new float[] { 1 }, 0, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1.1F, 2.1F, 3.1F }, null, 0, 0.2F));
        assertEquals(7, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 1 }, 8, 0.2F));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 8, 0.2F));
        assertEquals(
                4,
                ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F }, new float[] { 1,
                        2 }, 8, 0.2F));
        assertEquals(5, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 9, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, -1, 0.2F));
        assertEquals(0, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 1 }, 0, 0.2F));
        assertEquals(-1, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] { 2 }, 0, 0.2F));

        assertEquals(0, ArrayUtil.lastIndexOf(new float[] { 1.1F, 1.1F, 2.1F, 1.1F, 1.1F, 2.1F, 1.1F, 1.1F },
                new float[] {}, 0, 0.2F));
    }

    @Test
    public void containsFloat() {
        assertFalse(ArrayUtil.contains((float[]) null, 1));
        assertFalse(ArrayUtil.contains(new float[0], 1));
        assertTrue(ArrayUtil.contains(new float[] { 1, 2, 3 }, 1));
        assertFalse(ArrayUtil.contains(new float[] { 1, 2, 3 }, 26));
    }

    @Test
    public void containsFloatTolerance() {
        assertFalse(ArrayUtil.contains((float[]) null, 1, 0.2F));
        assertFalse(ArrayUtil.contains(new float[0], 1, 0.2F));
        assertTrue(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, 1, 0.2F));
        assertFalse(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, 26, 0.2F));
    }

    @Test
    public void containsFloatArray() {
        assertFalse(ArrayUtil.contains((float[]) null, new float[] { 1 }));
        assertFalse(ArrayUtil.contains(new float[] { 1, 2, 3 }, null));
        assertTrue(ArrayUtil.contains(new float[0], new float[0]));
        assertTrue(ArrayUtil.contains(new float[] { 1, 2, 3 }, new float[0]));
        assertTrue(ArrayUtil.contains(new float[] { 1, 2, 3 }, new float[] { 1 }));
        assertFalse(ArrayUtil.contains(new float[] { 1, 2, 3 }, new float[] { 26 }));
    }

    @Test
    public void containsFloatArrayTolerance() {
        assertFalse(ArrayUtil.contains((float[]) null, new float[] { 1 }, 0.2F));
        assertFalse(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, null, 0.2F));
        assertTrue(ArrayUtil.contains(new float[0], new float[0], 0.2F));
        assertTrue(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, new float[0], 0.2F));
        assertTrue(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, new float[] { 1 }, 0.2F));
        assertFalse(ArrayUtil.contains(new float[] { 1.1F, 2.1F, 3.1F }, new float[] { 26 }, 0.2F));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：boolean[]
    // ==========================================================================
    @Test
    public void indexOfBoolean() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((boolean[]) null, true));
        assertEquals(-1, ArrayUtil.indexOf(new boolean[0], true));
        assertEquals(0, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, true));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, false));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((boolean[]) null, true, 0));
        assertEquals(-1, ArrayUtil.indexOf(new boolean[0], true, 0));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 0));
        assertEquals(5, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 3));
        assertEquals(-1,
                ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 9));
        assertEquals(2,
                ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, -1));
    }

    @Test
    public void indexOfBooleanArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((boolean[]) null, new boolean[] { true }));
        assertEquals(-1, ArrayUtil.indexOf(new boolean[] { true, true, true }, null));
        assertEquals(0, ArrayUtil.indexOf(new boolean[0], new boolean[0]));
        assertEquals(0, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true }));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }));
        assertEquals(
                1,
                ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, new boolean[] {
                        true, false }));
        assertEquals(0,
                ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, new boolean[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((boolean[]) null, new boolean[] { true }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new boolean[] { true, true, true }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new boolean[0], new boolean[0], 0));
        assertEquals(0, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true }, 0));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 0));
        assertEquals(
                1,
                ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true }, new boolean[] {
                        true, false }, 0));
        assertEquals(5, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 3));
        assertEquals(-1, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 9));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, -1));
        assertEquals(2, ArrayUtil.indexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new boolean[] { true, true, true }, new boolean[0], 9));
    }

    @Test
    public void lastIndexOfBoolean() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((boolean[]) null, true));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[0], true));
        assertEquals(7, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, true));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((boolean[]) null, true, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[0], true, 0));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 8));
        assertEquals(2,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 4));
        assertEquals(-1,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 0));
        assertEquals(5,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, 9));
        assertEquals(-1,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, false, -1));
        assertEquals(0,
                ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true }, true, 0));
    }

    @Test
    public void lastIndexOfBooleanArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((boolean[]) null, new boolean[] { true }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[] { true, true, true }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[0], new boolean[] { true }));
        assertEquals(7, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true }));
        assertEquals(5, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }));
        assertEquals(5, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false, true }));

        assertEquals(8, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((boolean[]) null, new boolean[] { true }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[] { true, true, true }, null, 0));
        assertEquals(7, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 8));
        assertEquals(4, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true, false }, 8));
        assertEquals(5, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 9));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, -1));
        assertEquals(0, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { true }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] { false }, 0));

        assertEquals(0, ArrayUtil.lastIndexOf(new boolean[] { true, true, false, true, true, false, true, true },
                new boolean[] {}, 0));
    }

    @Test
    public void containsBoolean() {
        assertFalse(ArrayUtil.contains((boolean[]) null, true));
        assertFalse(ArrayUtil.contains(new boolean[0], true));
        assertTrue(ArrayUtil.contains(new boolean[] { true, true, true }, true));
        assertFalse(ArrayUtil.contains(new boolean[] { true, true, true }, false));
    }

    @Test
    public void containsBooleanArray() {
        assertFalse(ArrayUtil.contains((boolean[]) null, new boolean[] { true }));
        assertFalse(ArrayUtil.contains(new boolean[] { true, true, true }, null));
        assertTrue(ArrayUtil.contains(new boolean[0], new boolean[0]));
        assertTrue(ArrayUtil.contains(new boolean[] { true, true, true }, new boolean[0]));
        assertTrue(ArrayUtil.contains(new boolean[] { true, true, true }, new boolean[] { true }));
        assertFalse(ArrayUtil.contains(new boolean[] { true, true, true }, new boolean[] { false }));
    }

    // ==========================================================================
    // 在数组中查找一个元素或一个元素序列。
    //
    // 类型：char[]
    // ==========================================================================
    @Test
    public void indexOfChar() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((char[]) null, (char) 1));
        assertEquals(-1, ArrayUtil.indexOf(new char[0], (char) 1));
        assertEquals(
                0,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((char[]) null, (char) 1, 0));
        assertEquals(-1, ArrayUtil.indexOf(new char[0], (char) 1, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 2, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 2, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 2, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, (char) 2, -1));
    }

    @Test
    public void indexOfCharArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.indexOf((char[]) null, new char[] { (char) 1 }));
        assertEquals(-1, ArrayUtil.indexOf(new char[] { (char) 1, (char) 2, (char) 3 }, null));
        assertEquals(0, ArrayUtil.indexOf(new char[0], new char[0]));
        assertEquals(
                0,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 1 }));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 2 }));
        assertEquals(
                1,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 1, (char) 2 }));
        assertEquals(
                0,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[0]));

        // 形式2
        assertEquals(-1, ArrayUtil.indexOf((char[]) null, new char[] { (char) 1 }, 0));
        assertEquals(-1, ArrayUtil.indexOf(new char[] { (char) 1, (char) 2, (char) 3 }, null, 0));
        assertEquals(0, ArrayUtil.indexOf(new char[0], new char[0], 0));
        assertEquals(
                0,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 1 }, 0));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 2 }, 0));
        assertEquals(
                1,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 1, (char) 2 }, 0));
        assertEquals(
                5,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 2 }, 3));
        assertEquals(
                -1,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 2 }, 9));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[] { (char) 2 }, -1));
        assertEquals(
                2,
                ArrayUtil.indexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2, (char) 1,
                        (char) 1 }, new char[0], 2));
        assertEquals(3, ArrayUtil.indexOf(new char[] { (char) 1, (char) 2, (char) 3 }, new char[0], 9));
    }

    @Test
    public void lastIndexOfChar() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((char[]) null, (char) 1));
        assertEquals(-1, ArrayUtil.lastIndexOf(new char[0], (char) 1));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 1));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((char[]) null, (char) 1, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new char[0], (char) 1, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2, 8));
        assertEquals(
                2,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2, 4));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2, 0));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 2, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, (char) 1, 0));
    }

    @Test
    public void lastIndexOfCharArray() {
        // 形式1
        assertEquals(-1, ArrayUtil.lastIndexOf((char[]) null, new char[] { (char) 1 }));
        assertEquals(-1, ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 2, (char) 3 }, null));
        assertEquals(-1, ArrayUtil.lastIndexOf(new char[0], new char[] { (char) 1 }));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 1 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2 }));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2, (char) 1 }));

        assertEquals(
                8,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] {}));

        // 形式2
        assertEquals(-1, ArrayUtil.lastIndexOf((char[]) null, new char[] { (char) 1 }, 0));
        assertEquals(-1, ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 2, (char) 3 }, null, 0));
        assertEquals(
                7,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 1 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2 }, 8));
        assertEquals(
                4,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 1, (char) 2 }, 8));
        assertEquals(
                5,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2 }, 9));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2 }, -1));
        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 1 }, 0));
        assertEquals(
                -1,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] { (char) 2 }, 0));

        assertEquals(
                0,
                ArrayUtil.lastIndexOf(new char[] { (char) 1, (char) 1, (char) 2, (char) 1, (char) 1, (char) 2,
                        (char) 1, (char) 1 }, new char[] {}, 0));
    }

    @Test
    public void containsChar() {
        assertFalse(ArrayUtil.contains((char[]) null, (char) 1));
        assertFalse(ArrayUtil.contains(new char[0], (char) 1));
        assertTrue(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, (char) 1));
        assertFalse(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, (char) 26));
    }

    @Test
    public void containsCharArray() {
        assertFalse(ArrayUtil.contains((char[]) null, new char[] { (char) 1 }));
        assertFalse(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, null));
        assertTrue(ArrayUtil.contains(new char[0], new char[0]));
        assertTrue(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, new char[0]));
        assertTrue(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, new char[] { (char) 1 }));
        assertFalse(ArrayUtil.contains(new char[] { (char) 1, (char) 2, (char) 3 }, new char[] { (char) 26 }));
    }

    @Test
    public void testToString() {
        assertEquals("[]", ArrayUtil.toString(null));
        assertEquals("[]", ArrayUtil.toString(""));
        assertEquals("[]", ArrayUtil.toString(new char[0]));

        assertEquals("[true, false, true]", ArrayUtil.toString(new boolean[] { true, false, true }));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new byte[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new char[] { '1', '2', '3' }));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new int[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new long[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new short[] { 1, 2, 3 }));

        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new float[] { 1, 2, 3 }));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new double[] { 1, 2, 3 }));

        assertEquals("[true, false, true]", ArrayUtil.toString(new Boolean[] { true, false, true }));
        assertEquals("[null, false, true]", ArrayUtil.toString(new Boolean[] { null, false, true }));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Byte[] { 1, 2, 3 }));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Byte[] { 1, null, 3 }));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Character[] { '1', '2', '3' }));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Character[] { '1', null, '3' }));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Integer[] { 1, 2, 3 }));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Integer[] { 1, null, 3 }));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Long[] { 1L, 2L, 3L }));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Long[] { 1L, null, 3L }));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Short[] { 1, 2, 3 }));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Short[] { 1, null, 3 }));

        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new Float[] { 1f, 2f, 3f }));
        assertEquals("[1.0, null, 3.0]", ArrayUtil.toString(new Float[] { 1f, null, 3f }));

        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new Double[] { 1d, 2d, 3d }));
        assertEquals("[1.0, null, 3.0]", ArrayUtil.toString(new Double[] { 1d, null, 3d }));

        assertNull(ArrayUtil.toString(null, null, null));
        assertEquals("default", ArrayUtil.toString(null, "default", null));
        assertEquals("notArray", ArrayUtil.toString("notArray", null, null));
        assertEquals("notArray", ArrayUtil.toString("notArray", "default", null));
        assertEquals("notArray", ArrayUtil.toString("notArray", "default", "NULL"));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new int[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new int[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new int[] { 1, 2, 3 }, null, null));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Integer[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, NULL, 3]", ArrayUtil.toString(new Integer[] { 1, null, 3 }, null, "NULL"));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Integer[] { 1, null, 3 }, null, null));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new byte[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new byte[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new byte[] { 1, 2, 3 }, null, null));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Byte[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, NULL, 3]", ArrayUtil.toString(new Byte[] { 1, null, 3 }, null, "NULL"));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Byte[] { 1, null, 3 }, null, null));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new char[] { '1', '2', '3' }, "default", "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new char[] { '1', '2', '3' }, null, "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new char[] { '1', '2', '3' }, null, null));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Character[] { '1', '2', '3' }, "default", "NULL"));
        assertEquals("[1, NULL, 3]", ArrayUtil.toString(new Character[] { '1', null, '3' }, null, "NULL"));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Character[] { '1', null, '3' }, null, null));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new short[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new short[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new short[] { 1, 2, 3 }, null, null));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Short[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, NULL, 3]", ArrayUtil.toString(new Short[] { 1, null, 3 }, null, "NULL"));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Short[] { 1, null, 3 }, null, null));

        assertEquals("[1, 2, 3]", ArrayUtil.toString(new long[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new long[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new long[] { 1, 2, 3 }, null, null));
        assertEquals("[1, 2, 3]", ArrayUtil.toString(new Long[] { 1L, 2L, 3L }, "default", "NULL"));
        assertEquals("[1, NULL, 3]", ArrayUtil.toString(new Long[] { 1L, null, 3L }, null, "NULL"));
        assertEquals("[1, null, 3]", ArrayUtil.toString(new Long[] { 1L, null, 3L }, null, null));

        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new float[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new float[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new float[] { 1, 2, 3 }, null, null));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new Float[] { 1f, 2f, 3f }, "default", "NULL"));
        assertEquals("[1.0, NULL, 3.0]", ArrayUtil.toString(new Float[] { 1f, null, 3f }, null, "NULL"));
        assertEquals("[1.0, null, 3.0]", ArrayUtil.toString(new Float[] { 1f, null, 3f }, null, null));

        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new double[] { 1, 2, 3 }, "default", "NULL"));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new double[] { 1, 2, 3 }, null, "NULL"));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new double[] { 1, 2, 3 }, null, null));
        assertEquals("[1.0, 2.0, 3.0]", ArrayUtil.toString(new Double[] { 1d, 2d, 3d }, "default", "NULL"));
        assertEquals("[1.0, NULL, 3.0]", ArrayUtil.toString(new Double[] { 1d, null, 3d }, null, "NULL"));
        assertEquals("[1.0, null, 3.0]", ArrayUtil.toString(new Double[] { 1d, null, 3d }, null, null));

        assertEquals("[true, false, true]", ArrayUtil.toString(new boolean[] { true, false, true }, "default", "NULL"));
        assertEquals("[true, false, true]", ArrayUtil.toString(new boolean[] { true, false, true }, null, "NULL"));
        assertEquals("[true, false, true]", ArrayUtil.toString(new boolean[] { true, false, true }, null, null));
        assertEquals("[true, false, true]", ArrayUtil.toString(new Boolean[] { true, false, true }, "default", "NULL"));
        assertEquals("[true, NULL, true]", ArrayUtil.toString(new Boolean[] { true, null, true }, null, "NULL"));
        assertEquals("[true, null, true]", ArrayUtil.toString(new Boolean[] { true, null, true }, null, null));

        assertEquals("[a, b, c]", ArrayUtil.toString(new String[] { "a", "b", "c" }, null, null));
        assertEquals("[a, null, c]", ArrayUtil.toString(new String[] { "a", null, "c" }, null, null));
        assertEquals("[a, NULL, c]", ArrayUtil.toString(new String[] { "a", null, "c" }, null, "NULL"));

    }

    @Test
    public void toStringArray() {

    }

    @Test
    public void remove() {

    }

    @Test
    public void wrapperToPrimitive() {
        assertNull(ArrayUtil.wrapperToPrimitive(null));
        assertNull(ArrayUtil.wrapperToPrimitive(new String[0]));
        assertNull(ArrayUtil.wrapperToPrimitive(new String[] { "a", "b", "c" }));

        boolean[] booleanArray = ArrayUtil.wrapperToPrimitive(new Boolean[] { true, false, true });
        assertEquals(Arrays.toString(new boolean[] { true, false, true }), Arrays.toString(booleanArray));

        char[] charArray = ArrayUtil.wrapperToPrimitive(new Character[] { '1', '2', '3' });
        assertArrayEquals(new char[] { '1', '2', '3' }, charArray);

        byte[] byteArray = ArrayUtil.wrapperToPrimitive(new Byte[] { 1, 2, 3 });
        assertArrayEquals(new byte[] { 1, 2, 3 }, byteArray);

        short[] shortArray = ArrayUtil.wrapperToPrimitive(new Short[] { 1, 2, 3 });
        assertArrayEquals(new short[] { 1, 2, 3 }, shortArray);

        int[] intArray = ArrayUtil.wrapperToPrimitive(new Integer[] { 1, 2, 3 });
        assertArrayEquals(new int[] { 1, 2, 3 }, intArray);

        long[] longArray = ArrayUtil.wrapperToPrimitive(new Long[] { 1L, 2L, 3L });
        assertArrayEquals(new long[] { 1, 2, 3 }, longArray);

        float[] floatArray = ArrayUtil.wrapperToPrimitive(new Float[] { 1f, 2f, 3f });
        assertArrayEquals(new float[] { 1, 2, 3 }, floatArray, 0.001f);

        double[] doubleArray = ArrayUtil.wrapperToPrimitive(new Double[] { 1d, 2d, 3d });
        assertArrayEquals(new double[] { 1, 2, 3 }, doubleArray, 0.001d);
    }

    @Test
    public void primitiveToWrapper() {
        assertNull(ArrayUtil.primitiveToWrapper(null));
        assertNull(ArrayUtil.primitiveToWrapper(new String[0]));
        assertNull(ArrayUtil.primitiveToWrapper(new String[] { "a", "b", "c" }));
        assertNull(ArrayUtil.primitiveToWrapper(new Boolean[] { true, false, true }));

        Boolean[] booleanArray = ArrayUtil.primitiveToWrapper(new boolean[] { true, false, true });
        assertArrayEquals(new Boolean[] { true, false, true }, booleanArray);

        Character[] charArray = ArrayUtil.primitiveToWrapper(new char[] { '1', '2', '3' });
        assertArrayEquals(new Character[] { '1', '2', '3' }, charArray);

        Byte[] byteArray = ArrayUtil.primitiveToWrapper(new byte[] { 1, 2, 3 });
        assertArrayEquals(new Byte[] { 1, 2, 3 }, byteArray);

        Short[] shortArray = ArrayUtil.primitiveToWrapper(new short[] { 1, 2, 3 });
        assertArrayEquals(new Short[] { 1, 2, 3 }, shortArray);

        Integer[] intArray = ArrayUtil.primitiveToWrapper(new int[] { 1, 2, 3 });
        assertArrayEquals(new Integer[] { 1, 2, 3 }, intArray);

        Long[] longArray = ArrayUtil.primitiveToWrapper(new long[] { 1L, 2L, 3L });
        assertArrayEquals(new Long[] { 1L, 2L, 3L }, longArray);

        Float[] floatArray = ArrayUtil.primitiveToWrapper(new float[] { 1f, 2f, 3f });
        assertArrayEquals(new Float[] { 1f, 2f, 3f }, floatArray);

        Double[] doubleArray = ArrayUtil.primitiveToWrapper(new double[] { 1d, 2d, 3d });
        assertArrayEquals(new Double[] { 1d, 2d, 3d }, doubleArray);

    }

    @Test
    public void addAll() {

    }

    @Test
    public void doubleToBoolean() {

    }

    @Test
    public void longToBoolean() {

    }

    @Test
    public void booleanToFloat() {

    }

    @Test
    public void booleanToLong() {

    }

    @Test
    public void booleanToShort() {

    }

    @Test
    public void booleanToChar() {

    }

    @Test
    public void booleanToInt() {

    }

    @Test
    public void byteToBoolean() {

    }

    @Test
    public void charToBoolean() {

    }

    @Test
    public void resize() {

    }

    @Test
    public void shortToBoolean() {

    }

    @Test
    public void doubleToChar() {

    }

    @Test
    public void doubleToShort() {

    }

    @Test
    public void floatToChar() {

    }

    @Test
    public void floatToShort() {

    }

    @Test
    public void longToChar() {

    }

    @Test
    public void longToShort() {

    }

    @Test
    public void booleanToString() {

    }

    @Test
    public void byteToChar() {

    }

    @Test
    public void byteToLong() {

    }

    @Test
    public void byteToString() {

    }

    @Test
    public void charToByte() {

    }

    @Test
    public void charToDouble() {

    }

    @Test
    public void charToFloat() {

    }

    @Test
    public void charToLong() {

    }

    @Test
    public void charToShort() {

    }

    @Test
    public void charToString() {

    }

    @Test
    public void intToChar() {

    }

    @Test
    public void shortToChar() {

    }

    @Test
    public void shortToLong() {

    }

    @Test
    public void shortToString() {

    }

    @Test
    public void byteToInt() {

    }

    @Test
    public void charToInt() {

    }

    @Test
    public void shortToInt() {

    }

    @Test
    public void subarray() {

    }

    @Test
    public void removeElement() {

    }

    @Test
    public void create() {

    }

    @Test
    public void testClone() {

    }

    @Test
    public void getLength() {

    }

}
