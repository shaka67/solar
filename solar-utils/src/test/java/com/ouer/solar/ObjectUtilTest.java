/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.junit.Test;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.ObjectUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月21日 上午3:29:10
 */
public class ObjectUtilTest {

    @Test
    public void defaultIfNull() {
        int[] ia = new int[0];
        Class<?>[] ic = new Class[0];
        String[] is = new String[0];
        String s = "";

        assertSame(ia, ObjectUtil.defaultIfNull(null, ia));
        assertSame(ic, ObjectUtil.defaultIfNull(null, ic));
        assertSame(is, ObjectUtil.defaultIfNull(null, is));
        assertSame(s, ObjectUtil.defaultIfNull(null, s));
        assertSame(s, ObjectUtil.defaultIfNull((Object) null, s));

        assertEquals("123", ObjectUtil.defaultIfNull("123", s));
    }

    @Test
    public void isSameType() {
        assertTrue(ObjectUtil.isSameType(null, null));
        assertTrue(ObjectUtil.isSameType(null, Boolean.TRUE));
        assertTrue(ObjectUtil.isSameType(Boolean.TRUE, null));
        assertTrue(ObjectUtil.isSameType(Boolean.TRUE, Boolean.FALSE));
        assertFalse(ObjectUtil.isSameType(Boolean.TRUE, new Integer(0)));
    }

    @Test
    public void isEmpty() {
        // object or null
        assertTrue(ObjectUtil.isEmpty(null));
        assertFalse(ObjectUtil.isEmpty(0));

        // String
        assertTrue(ObjectUtil.isEmpty(""));
        assertFalse(ObjectUtil.isEmpty(" "));
        assertFalse(ObjectUtil.isEmpty("bob"));
        assertFalse(ObjectUtil.isEmpty("  bob  "));
        assertFalse(ObjectUtil.isEmpty("\u3000")); // unicode blank
        assertFalse(ObjectUtil.isEmpty("\r\n")); // blank

        // array
        assertTrue(ObjectUtil.isEmpty(new Object[0]));
        assertTrue(ObjectUtil.isEmpty(new int[0]));
        assertFalse(ObjectUtil.isEmpty(new int[] { 1, 2 }));
    }

    @Test
    public void testNull() {
        // all null
        Object[] NULL = null;
        assertTrue(ObjectUtil.isAllNull(NULL));
        assertTrue(ObjectUtil.isAllNull(new Object[] { null, null, null, null, null }));
        assertFalse(ObjectUtil.isEmpty(new Object[] { null, null, 0, null, null }));

        // any null
        assertTrue(ObjectUtil.isAnyNull(NULL));
        assertTrue(ObjectUtil.isAnyNull(new Object[] { null, null, null, null, null }));
        assertTrue(ObjectUtil.isAnyNull(new Object[] { null, null, 0, null, null }));
        assertTrue(ObjectUtil.isAnyNull(new Object[] { null, "null", 0, null, null }));
        assertFalse(ObjectUtil
                .isAnyNull(new Object[] { "", "null", 0, new int[] {}, CollectionUtil.createArrayList() }));
    }

    // ==========================================================================
    // 比较函数。
    //
    // 以下方法用来比较两个对象的值或类型是否相同。
    // ==========================================================================

    @Test
    public void isEquals() throws Exception {
        assertTrue(ObjectUtil.isEquals(null, null));
        assertFalse(ObjectUtil.isEquals(null, ""));
        assertFalse(ObjectUtil.isEquals("", null));
        assertTrue(ObjectUtil.isEquals("", ""));
        assertFalse(ObjectUtil.isEquals(Boolean.TRUE, null));
        assertFalse(ObjectUtil.isEquals(Boolean.TRUE, "true"));
        assertTrue(ObjectUtil.isEquals(Boolean.TRUE, Boolean.TRUE));
        assertFalse(ObjectUtil.isEquals(Boolean.TRUE, Boolean.FALSE));

        Object[] oa = new Object[] { new MyObject(), new MyObject() };
        int[] ia = new int[] { 1, 2, 3 };
        long[] la = new long[] { 1, 2, 3 };
        short[] sa = new short[] { 1, 2, 3 };
        byte[] ba = new byte[] { 1, 2, 3 };
        double[] da = new double[] { 1, 2, 3 };
        float[] fa = new float[] { 1, 2, 3 };
        boolean[] bla = new boolean[] { true, false, true };
        char[] ca = new char[] { 'a', 'b', 'c' };
        Object[] combo = { oa, ia, la, sa, ba, da, fa, bla, ca, null };

        assertObjectEquals(oa);
        assertObjectEquals(ia);
        assertObjectEquals(la);
        assertObjectEquals(sa);
        assertObjectEquals(ba);
        assertObjectEquals(da);
        assertObjectEquals(fa);
        assertObjectEquals(bla);
        assertObjectEquals(ca);
        assertObjectEquals(combo);
    }

    private void assertObjectEquals(Object array) throws Exception {
        Method clone = Object.class.getDeclaredMethod("clone");
        clone.setAccessible(true);

        assertTrue(ObjectUtil.isEquals(array, array)); // same
        assertTrue(ObjectUtil.isEquals(array, clone.invoke(array))); // equals to copy

        Object copy = Array.newInstance(array.getClass().getComponentType(), Array.getLength(array) - 1);
        System.arraycopy(array, 0, copy, 0, Array.getLength(copy));

        assertFalse(ObjectUtil.isEquals(array, copy)); // not equals
    }

    // ==========================================================================
    // Hash code函数。
    //
    // 以下方法用来取得对象的hash code。
    // ==========================================================================

    @Test
    public void _hashCode() {
        assertEquals(0, ObjectUtil.hashCode(null));
        assertEquals(123, ObjectUtil.hashCode(new MyObject()));

        assertEquals(4897, ObjectUtil.hashCode(new Object[] { new MyObject(), new MyObject() }));
        assertEquals(30817, ObjectUtil.hashCode(new int[] { 1, 2, 3 }));
        assertEquals(30817, ObjectUtil.hashCode(new long[] { 1, 2, 3 }));
        assertEquals(30817, ObjectUtil.hashCode(new short[] { 1, 2, 3 }));
        assertEquals(30817, ObjectUtil.hashCode(new byte[] { 1, 2, 3 }));
        assertEquals(66614367, ObjectUtil.hashCode(new double[] { 1, 2, 3 }));
        assertEquals(1606448223, ObjectUtil.hashCode(new float[] { 1, 2, 3 }));
        assertEquals(1252360, ObjectUtil.hashCode(new boolean[] { true, false, true }));
        assertEquals(126145, ObjectUtil.hashCode(new char[] { 'a', 'b', 'c' }));
    }

    private class MyObject {
        @Override
        public int hashCode() {
            return 123;
        }
    }

    // ==========================================================================
    // 取得对象的identity。
    // ==========================================================================

    @Test
    public void identityHashCode() {
        assertEquals(0, ObjectUtil.identityHashCode(null));

        MyObject obj = new MyObject();

        assertFalse(123 == ObjectUtil.identityHashCode(obj));
        assertEquals(System.identityHashCode(obj), ObjectUtil.identityHashCode(obj));
    }

    // @Test
    // public void identityToString() {
    // assertNull(ObjectUtil.identityToString(null));
    // assertTrue(ObjectUtil.identityToString("").startsWith("java.lang.String@"));
    // assertTrue(ObjectUtil.identityToString(Boolean.TRUE).startsWith("java.lang.Boolean@"));
    // assertTrue(ObjectUtil.identityToString(new int[0]).startsWith("int[]@"));
    // assertTrue(ObjectUtil.identityToString(new Object[0]).startsWith("java.lang.Object[]@"));
    // assertTrue(ObjectUtil.identityToString(new Object[0][]).startsWith("java.lang.Object[][]@"));
    //
    // assertEquals(System.identityHashCode(Boolean.TRUE), Integer.parseInt(ObjectUtil.identityToString(Boolean.TRUE)
    // .substring("java.lang.Boolean@".length()), 16));
    // }

    // @Test
    // public void identityToStringNullStr() {
    // assertEquals("NULL", ObjectUtil.identityToString(null, "NULL"));
    // assertTrue(ObjectUtil.identityToString("", "NULL").startsWith("java.lang.String@"));
    // assertTrue(ObjectUtil.identityToString(Boolean.TRUE, "NULL").startsWith("java.lang.Boolean@"));
    // assertTrue(ObjectUtil.identityToString(new int[0], "NULL").startsWith("int[]@"));
    // assertTrue(ObjectUtil.identityToString(new Object[0], "NULL").startsWith("java.lang.Object[]@"));
    // assertTrue(ObjectUtil.identityToString(new Object[0][], "NULL").startsWith("java.lang.Object[][]@"));
    //
    // assertEquals(
    // System.identityHashCode(Boolean.TRUE),
    // Integer.parseInt(
    // ObjectUtil.identityToString(Boolean.TRUE, "NULL").substring("java.lang.Boolean@".length()), 16));
    // }

    // @Test
    // public void appendIdentityToString() {
    // try {
    // ObjectUtil.appendIdentityToString((StringBuilder) null, null);
    // fail();
    // } catch (IllegalArgumentException e) {
    // assertThat(e, TestUtil.exception("appendable"));
    // }
    //
    // assertEquals("null", ObjectUtil.appendIdentityToString(new StringBuilder(), null).toString());
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuilder(), "").toString()
    // .startsWith("java.lang.String@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuilder(), Boolean.TRUE).toString()
    // .startsWith("java.lang.Boolean@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuilder(), new int[0]).toString().startsWith("int[]@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuilder(), new Object[0]).toString()
    // .startsWith("java.lang.Object[]@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuilder(), new Object[0][]).toString()
    // .startsWith("java.lang.Object[][]@"));
    //
    // StringBuilder builder = new StringBuilder();
    // StringBuilder builder1 = ObjectUtil.appendIdentityToString(builder, Boolean.TRUE);
    //
    // assertSame(builder, builder1);
    // assertTrue(builder.toString().startsWith("java.lang.Boolean@"));
    //
    // try {
    // ObjectUtil.appendIdentityToString((StringBuffer) null, null);
    // fail();
    // } catch (IllegalArgumentException e) {
    // assertThat(e, TestUtil.exception("appendable"));
    // }
    //
    // assertEquals("null", ObjectUtil.appendIdentityToString(new StringBuffer(), null).toString());
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuffer(), "").toString().startsWith("java.lang.String@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuffer(), Boolean.TRUE).toString()
    // .startsWith("java.lang.Boolean@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuffer(), new int[0]).toString().startsWith("int[]@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuffer(), new Object[0]).toString()
    // .startsWith("java.lang.Object[]@"));
    // assertTrue(ObjectUtil.appendIdentityToString(new StringBuffer(), new Object[0][]).toString()
    // .startsWith("java.lang.Object[][]@"));
    //
    // StringBuffer buffer = new StringBuffer();
    // StringBuffer buffer1 = ObjectUtil.appendIdentityToString(buffer, Boolean.TRUE);
    //
    // assertSame(buffer, buffer1);
    // assertTrue(buffer.toString().startsWith("java.lang.Boolean@"));
    // }

    // ==========================================================================
    // toString方法。
    // ==========================================================================

    @Test
    public void _toString() {
        assertEquals("", ObjectUtil.toString(null));
        assertEquals("", ObjectUtil.toString(""));
        assertEquals("bat", ObjectUtil.toString("bat"));
        assertEquals("true", ObjectUtil.toString(Boolean.TRUE));

        assertEquals("[1, 2, 3]", ObjectUtil.toString(new int[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ObjectUtil.toString(new long[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ObjectUtil.toString(new short[] { 1, 2, 3 }));
        assertEquals("[1, 2, 3]", ObjectUtil.toString(new byte[] { 1, 2, 3 }));
        assertEquals("[1.0, 2.0, 3.0]", ObjectUtil.toString(new double[] { 1, 2, 3 }));
        assertEquals("[1.0, 2.0, 3.0]", ObjectUtil.toString(new float[] { 1, 2, 3 }));
        assertEquals("[true, false, true]", ObjectUtil.toString(new boolean[] { true, false, true }));
        assertEquals("[a, b, c]", ObjectUtil.toString(new char[] { 'a', 'b', 'c' }));

        assertEquals(
                "[[1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3], [1.0, 2.0, 3.0], [1.0, 2.0, 3.0], [true, false, true], [a, b, c]]",
                ObjectUtil.toString(new Object[] { //
                        new int[] { 1, 2, 3 }, //
                                new long[] { 1, 2, 3 }, //
                                new short[] { 1, 2, 3 }, //
                                new byte[] { 1, 2, 3 }, //
                                new double[] { 1, 2, 3 }, //
                                new float[] { 1, 2, 3 }, //
                                new boolean[] { true, false, true }, //
                                new char[] { 'a', 'b', 'c' }, //
                        }));
    }

    @Test
    public void toStringNullStr() {
        assertNull(ObjectUtil.toString(null, null));
        assertEquals("null", ObjectUtil.toString(null, "null"));
        assertEquals("", ObjectUtil.toString("", "null"));
        assertEquals("bat", ObjectUtil.toString("bat", "null"));
        assertEquals("true", ObjectUtil.toString(Boolean.TRUE, "null"));
        assertEquals("[1, 2, 3]", ObjectUtil.toString(new int[] { 1, 2, 3 }, "null"));
    }

}
