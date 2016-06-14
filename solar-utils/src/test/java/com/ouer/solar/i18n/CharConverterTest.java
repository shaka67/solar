/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.i18n.CharConvertReader;
import com.ouer.solar.i18n.CharConvertWriter;
import com.ouer.solar.i18n.CharConverter;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月20日 上午3:54:49
 */
public class CharConverterTest {
    private CharConverter simpConverter;
    private CharConverter tradConverter;
    private String simp = "我爱北京天安门，天安门上太阳升！1234567890abcdefghijklmnopqrstuvwxyz";
    private String trad =
            "\u6211\u611B\u5317\u4EAC\u5929\u5B89\u9580\uFF0C\u5929\u5B89\u9580\u4E0A\u592A\u967D\u5347\uFF011234567890abcdefghijklmnopqrstuvwxyz";

    @Before
    public void init() throws IOException {
        simpConverter = CharConverter.getInstance(CharConverter.TRADITIONAL_TO_SIMPLIFIED_CHINESE);
        tradConverter = CharConverter.getInstance(CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);
    }

    @Test
    public void tradConverter() {
        assertConverter(simp, trad, tradConverter);
    }

    @Test
    public void simpConverter() {
        assertConverter(trad, simp, simpConverter);
    }

    private void assertConverter(String src, String dest, CharConverter converter) {
        // convert char
        for (int i = 0; i < src.length(); i++) {
            assertEquals(dest.charAt(i), converter.convert(src.charAt(i)));
        }

        // convert String
        assertEquals(dest, converter.convert(src));
        assertEquals(dest.substring(1, 11), converter.convert(src, 1, 10));

        // convert char[]
        char[] chars;

        chars = src.toCharArray();
        converter.convert(chars);
        assertEquals(dest, new String(chars));

        chars = src.toCharArray();
        converter.convert(chars, 1, 10);
        assertEquals(src.substring(0, 1) + dest.substring(1, 11) + src.substring(11, src.length()), new String(chars));
    }

    // @Test
    // public void dumpAll() throws Exception {
    // File destfile = new File(destdir, "dump_all.txt");
    //
    // System.out.println("Dump all chars to " + destfile.getAbsolutePath());
    //
    // PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(destfile), "GB18030"));
    // long startTime = System.currentTimeMillis();
    // int total = 0;
    //
    // // Dump unicode中的简体:
    // out.println("==================================================");
    // out.println(" 1. Unicode中的简体字：");
    // out.println("--------------------------------------------------");
    //
    // int count = 0;
    //
    // for (int i = 0; i < 65536; i++) {
    // char ch = (char) i;
    // char simp = simpConverter.convert(ch);
    // char trad = tradConverter.convert(ch);
    //
    // if (simp == ch && trad != ch) {
    // out.println("Unicode(" + hex(i) + " - " + ch + "): Simplified(" + simp + "), Traditional(" + trad + ")");
    // count++;
    // }
    // }
    //
    // out.println("--------------------------------------------------");
    // out.println(" " + count + " words\n");
    // total += count;
    //
    // // Dump unicode中的繁体:
    // out.println("==================================================");
    // out.println(" 2. Unicode中的繁体字：");
    // out.println("--------------------------------------------------");
    //
    // count = 0;
    //
    // for (int i = 0; i < 65536; i++) {
    // char ch = (char) i;
    // char simp = simpConverter.convert(ch);
    // char trad = tradConverter.convert(ch);
    //
    // if (simp != ch && trad == ch) {
    // out.println("Unicode(" + hex(i) + " - " + ch + "): Simplified(" + simp + "), Traditional(" + trad + ")");
    // count++;
    // }
    // }
    //
    // out.println("--------------------------------------------------");
    // out.println(" " + count + " words\n");
    // total += count;
    //
    // // Dump繁简不同的字:
    // out.println("==================================================");
    // out.println(" 3. Unicode中的繁简体均不同的字：");
    // out.println("--------------------------------------------------");
    //
    // count = 0;
    //
    // for (int i = 0; i < 65536; i++) {
    // char ch = (char) i;
    // char simp = simpConverter.convert(ch);
    // char trad = tradConverter.convert(ch);
    //
    // if (simp != ch && trad != ch && simp != trad) {
    // out.println("Unicode(" + hex(i) + " - " + ch + "): Simplified(" + simp + "), Traditional(" + trad + ")");
    // count++;
    // }
    // }
    //
    // out.println("--------------------------------------------------");
    // out.println(" " + count + " words\n");
    // total += count;
    //
    // out.println("==================================================");
    // out.println(" Total: " + total + " words\n");
    //
    // long duration = System.currentTimeMillis() - startTime;
    //
    // out.close();
    // System.out.println("Done in " + duration + "ms");
    // }

    @Test
    public void charConvertReader() throws IOException {
        Reader in;

        // read()
        in = new CharConvertReader(new StringReader(simp), CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);

        StringBuilder builder = new StringBuilder();
        int ch;

        while ((ch = in.read()) != -1) {
            builder.append((char) ch);
        }

        assertEquals(trad, builder.toString());

        // read(char[])
        in = new CharConvertReader(new StringReader(simp), CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);

        char[] cbuf = new char[5];
        int count;

        builder = new StringBuilder();

        while ((count = in.read(cbuf)) != -1) {
            builder.append(cbuf, 0, count);
        }

        assertEquals(trad, builder.toString());
    }

    @Test
    public void charConvertWriter() throws IOException {
        StringWriter buf;
        Writer out;

        // write(int)
        buf = new StringWriter();
        out = new CharConvertWriter(buf, CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);

        for (int i = 0; i < simp.length(); i++) {
            int ch = simp.charAt(i);

            out.write(ch);
        }

        assertEquals(trad, buf.toString());

        // write(char[])
        buf = new StringWriter();
        out = new CharConvertWriter(buf, CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);

        out.write(simp.toCharArray(), 1, 10);
        out.write(simp.toCharArray(), 11, 2);
        assertEquals(trad.substring(1, 13), buf.toString());

        // write(String)
        buf = new StringWriter();
        out = new CharConvertWriter(buf, CharConverter.SIMPLIFIED_TO_TRADITIONAL_CHINESE);

        out.write(simp, 1, 10);
        out.write(simp, 11, 2);
        assertEquals(trad.substring(1, 13), buf.toString());

    }

    protected static String hex(int i) {
        String value = Integer.toHexString(i).toUpperCase();
        int length = value.length();

        switch (length) {
            case 1:
                return "000" + value;

            case 2:
                return "00" + value;

            case 3:
                return "0" + value;

            case 4:
                return value;

            default:
                throw new IllegalArgumentException(String.valueOf(value));
        }
    }
}
