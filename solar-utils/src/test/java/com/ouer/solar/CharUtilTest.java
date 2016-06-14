/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.ouer.solar.CharUtil;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午2:24:26
 */
public class CharUtilTest extends CachedLogger {

    @Test
    public void toAscii() {
        assertEquals(0x3F, CharUtil.toAscii('你'));
        assertEquals(0x3F, CharUtil.toAscii('我'));

        assertEquals(48, CharUtil.toAscii('0'));

        assertEquals(9, CharUtil.toAscii('9') - CharUtil.toAscii('0'));
    }

    @Test
    public void isWhitespace() {
        assertTrue(CharUtil.isWhitespace(' '));
        assertTrue(CharUtil.isWhitespace('\t'));
        assertTrue(CharUtil.isWhitespace('\r'));
        assertTrue(CharUtil.isWhitespace('\n'));

        assertFalse(CharUtil.isWhitespace('a'));
        assertFalse(CharUtil.isWhitespace('0'));
        assertFalse(CharUtil.isWhitespace('我'));
    }

    @Test
    public void toSimpleByteArray() {
        char[] src = new char[] { 0, 10, 'A', 127, 128, 255, 256 };
        byte[] dest = CharUtil.toSimpleByteArray(src);

        assertEquals(0, dest[0]);
        assertEquals(10, dest[1]);
        assertEquals(65, dest[2]);
        assertEquals(127, dest[3]);
        assertEquals(-128, dest[4]);
        assertEquals(-1, dest[5]);
        assertEquals(0, dest[6]);
    }

    @Test
    public void toSimpleCharArray() {
        byte[] src = new byte[] { 0, 10, 65, 127, -128, -1 };
        char[] dest = CharUtil.toSimpleCharArray(src);

        assertEquals(0, dest[0]);
        assertEquals(10, dest[1]);
        assertEquals('A', dest[2]);
        assertEquals(127, dest[3]);
        assertEquals(128, dest[4]);
        assertEquals(255, dest[5]);
    }

    @Test
    public void toAsciiByteArray() {
        char[] src = new char[] { 0, 10, 'A', 127, 128, 255, 256 };
        byte[] dest = CharUtil.toAsciiByteArray(src);

        assertEquals(0, dest[0]);
        assertEquals(10, dest[1]);
        assertEquals(65, dest[2]);
        assertEquals(127, dest[3]);
        assertEquals(-128, dest[4]);
        assertEquals(-1, dest[5]);
        assertEquals(0x3F, dest[6]);
    }

    @Test
    public void toRawByteArray() {
        char[] src = new char[] { 0, 'A', 255, 256, 0xFF7F };
        byte[] dest = CharUtil.toRawByteArray(src);

        assertEquals(src.length * 2, dest.length);

        assertEquals(0, dest[0]);
        assertEquals(0, dest[1]);

        assertEquals(0, dest[2]);
        assertEquals(65, dest[3]);

        assertEquals(0, dest[4]);
        assertEquals(-1, dest[5]);

        assertEquals(1, dest[6]);
        assertEquals(0, dest[7]);

        assertEquals(-1, dest[8]);
        assertEquals(127, dest[9]);
    }

    @Test
    public void toRawCharArray() {
        byte[] src = new byte[] { 0, 0, 0, 65, 0, -1, 1, 0, -1 };
        char[] dest = CharUtil.toRawCharArray(src);

        assertEquals(src.length / 2 + src.length % 2, dest.length);

        assertEquals(0, dest[0]);
        assertEquals('A', dest[1]);
        assertEquals(255, dest[2]);
        assertEquals(256, dest[3]);
        assertEquals(0xFF00, dest[4]);

    }

    @Test
    public void toByte() throws UnsupportedEncodingException {
        char[] src = "tstč".toCharArray();
        assertEquals(4, src.length);
        assertEquals(269, src[3]);

        byte[] dest = CharUtil.toSimpleByteArray(src);
        assertEquals(4, dest.length);
        assertEquals(269 - 256, dest[3]);
        char[] src2 = CharUtil.toSimpleCharArray(dest);
        assertEquals(4, src2.length);
        assertTrue(src[3] != src2[3]);

        byte[] dest2 = CharUtil.toByteArray(src, "US-ASCII");
        assertEquals(4, dest2.length);
        assertEquals(0x3F, dest2[3]);

        byte[] dest3 = CharUtil.toAsciiByteArray(src);
        assertEquals(4, dest3.length);
        assertEquals(0x3F, dest3[3]);

        dest = CharUtil.toByteArray(src, "UTF16");
        assertEquals(8 + 2, dest.length); // BOM included
        assertEquals(269 - 256, dest[9]);
        assertEquals(1, dest[8]);
        src2 = CharUtil.toCharArray(dest, "UTF16");
        assertEquals(src[3], src2[3]);

        dest = CharUtil.toByteArray(src, "UTF8");
        assertEquals(5, dest.length);
    }

    @Test
    public void hexToInt() {
        assertEquals(0, CharUtil.hexToInt('0'));
        assertEquals(1, CharUtil.hexToInt('1'));
        assertEquals(2, CharUtil.hexToInt('2'));
        assertEquals(3, CharUtil.hexToInt('3'));
        assertEquals(4, CharUtil.hexToInt('4'));
        assertEquals(5, CharUtil.hexToInt('5'));
        assertEquals(6, CharUtil.hexToInt('6'));
        assertEquals(7, CharUtil.hexToInt('7'));
        assertEquals(8, CharUtil.hexToInt('8'));
        assertEquals(9, CharUtil.hexToInt('9'));
        assertEquals(10, CharUtil.hexToInt('A'));
        assertEquals(10, CharUtil.hexToInt('a'));
        assertEquals(11, CharUtil.hexToInt('B'));
        assertEquals(11, CharUtil.hexToInt('b'));
        assertEquals(12, CharUtil.hexToInt('C'));
        assertEquals(12, CharUtil.hexToInt('c'));
        assertEquals(13, CharUtil.hexToInt('D'));
        assertEquals(13, CharUtil.hexToInt('d'));
        assertEquals(14, CharUtil.hexToInt('E'));
        assertEquals(14, CharUtil.hexToInt('e'));
        assertEquals(15, CharUtil.hexToInt('F'));
        assertEquals(15, CharUtil.hexToInt('f'));
    }

}
