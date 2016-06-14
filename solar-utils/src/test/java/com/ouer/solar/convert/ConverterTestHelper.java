/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.ouer.solar.mutable.MutableInteger;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午3:12:37
 */
public final class ConverterTestHelper {

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

    public static byte[] arrb(int...v) {
        byte[] bytes = new byte[v.length];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) v[i];
        }

        return bytes;
    }

    public static short[] arrs(int...v) {
        short[] shorts = new short[v.length];

        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = (short) v[i];
        }

        return shorts;
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

    public static <T> ArrayList<T> listo(T...v) {
        ArrayList<T> list = new ArrayList<T>(v.length);

        for (int i = 0; i < v.length; i++) {
            list.add(v[i]);
        }

        return list;
    }

    public static <T> HashSet<T> seto(T...v) {
        HashSet<T> set = new LinkedHashSet<T>(v.length);

        for (int i = 0; i < v.length; i++) {
            set.add(v[i]);
        }

        return set;
    }

    public static <T> Iterable<T> iterableo(final T...v) {
        return new Iterable<T>() {
            @Override
			public Iterator<T> iterator() {
                final MutableInteger index = new MutableInteger(0);
                return new Iterator<T>() {
                    @Override
					public boolean hasNext() {
                        return index.value < v.length;
                    }

                    @Override
					public T next() {
                        T value = v[index.value];
                        index.value++;
                        return value;
                    }

                    @Override
					public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

}
