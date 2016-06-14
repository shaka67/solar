/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import static com.ouer.solar.convert.AssertArraysTestHelper.arri;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:50:12
 */
public class MatrixTest {

    // private List<Integer> intsList(int... array) {
    // ArrayList<Integer> list = new ArrayList<Integer>();
    // for (int i : array) {
    // list.add(Integer.valueOf(i));
    // }
    // return list;
    // }
    //
    // @Test
    // public void testIntMatrix2() {
    // ArrayList<List<Integer>> matrix = new ArrayList<List<Integer>>();
    //
    // matrix.add(intsList(1, 2, 3));
    // matrix.add(intsList(9, 8, 7));
    //
    // int[][] arr = ConverterManager.convertType(matrix, int[][].class);
    //
    // assertEquals(2, arr.length);
    //
    // assertArrayEquals(arri(1, 2, 3), arr[0]);
    // assertArrayEquals(arri(9, 8, 7), arr[1]);
    // }

    @Test
    public void testStringToIntMatrix() {
        String[][] strings = new String[][] { { "123", "865" }, { "432", "345", "9832" } };

        int[][] arr = ConverterManager.convertType(strings, int[][].class);

        assertEquals(2, arr.length);

        assertArrayEquals(arri(123, 865), arr[0]);
        assertArrayEquals(arri(432, 345, 9832), arr[1]);
    }

    @Test
    public void testIntToStringMatrix() {
        int[][] values = new int[][] { { 123, 865 }, { 432, 345, 9832 } };

        String[][] arr = ConverterManager.convertType(values, String[][].class);

        assertEquals(2, arr.length);

        assertEquals("123", arr[0][0]);
        assertEquals("865", arr[0][1]);

        assertEquals("432", arr[1][0]);
        assertEquals("345", arr[1][1]);
        assertEquals("9832", arr[1][2]);
    }
}