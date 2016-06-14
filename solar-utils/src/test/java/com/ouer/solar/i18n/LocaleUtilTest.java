/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.i18n.LocaleInfo;
import com.ouer.solar.i18n.LocaleUtil;
import com.ouer.solar.i18n.LocaleUtil.Notifier;
import com.ouer.solar.test.TestUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月21日 上午8:31:05
 */
public class LocaleUtilTest {

    private Locale systemLocale;
    private String systemCharset;

    @Before
    public void init() {
        systemLocale = Locale.getDefault();
        systemCharset = Charset.defaultCharset().name();
    }

    @After
    public void destroy() {
        LocaleUtil.resetDefault();
        LocaleUtil.resetContext();
    }

    @Test
    public void isLocaleSupported() {
        assertTrue(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("zh")));
        assertTrue(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("zh_CN")));
        assertTrue(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("zh_CN_aa")));

        assertFalse(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("aa")));
        assertFalse(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("aa_bb")));
        assertFalse(LocaleUtil.isLocaleSupported(LocaleUtil.parseLocale("aa_bb_cc")));
    }

    @Test
    public void isCharsetSupported() {
        assertTrue(LocaleUtil.isCharsetSupported("GBK"));
        assertTrue(LocaleUtil.isCharsetSupported("8859_1"));
        assertTrue(LocaleUtil.isCharsetSupported("iso8859_1"));
        assertTrue(LocaleUtil.isCharsetSupported("iso-8859-1"));

        assertFalse(LocaleUtil.isCharsetSupported(null));
        assertFalse(LocaleUtil.isCharsetSupported("hello"));
    }

    @Test
    public void parseLocale() {
        assertNull(LocaleUtil.parseLocale(null));
        assertNull(LocaleUtil.parseLocale(""));
        assertNull(LocaleUtil.parseLocale("  "));

        assertEquals(new Locale("zh"), LocaleUtil.parseLocale(" zh "));
        assertEquals(new Locale("zh", "CN"), LocaleUtil.parseLocale(" zh _CN"));
        assertEquals(new Locale("zh", "CN", "var"), LocaleUtil.parseLocale(" zh _CN_var"));
        assertEquals(new Locale("zh", "CN", "var_xxx"), LocaleUtil.parseLocale(" zh _CN_var_xxx"));

        assertEquals(new Locale("zh", "", "CN_var_xxx"), LocaleUtil.parseLocale(" zh__CN_var_xxx"));
    }

    @Test
    public void getCanonicalCharset() {
        assertEquals("ISO-8859-1", LocaleUtil.getCanonicalCharset("8859_1"));
        assertEquals("ISO-8859-1", LocaleUtil.getCanonicalCharset("ISO8859_1"));

        try {
            LocaleUtil.getCanonicalCharset("aaaabbbbcccc"); // UnsupportedCharsetException
            fail("Expected UnsupportedCharsetException");
        } catch (UnsupportedCharsetException e) {
        }

        try {
            LocaleUtil.getCanonicalCharset("aaaa<bbbb>cccc"); // IllegalCharsetNameException
            fail("Expected IllegalCharsetNameException");
        } catch (IllegalCharsetNameException e) {
        }
    }

    @Test
    public void calculateBundleNames() {
        // 空baseName
        assertBundleNames("", null, null, null, LocaleUtil.calculateBundleNames("", null));
        assertBundleNames("", null, null, null, LocaleUtil.calculateBundleNames("", new Locale("")));
        assertBundleNames("", "zh", null, null, LocaleUtil.calculateBundleNames("", new Locale("zh")));
        assertBundleNames("", "zh", "zh_CN", null, LocaleUtil.calculateBundleNames("", new Locale("zh", "CN")));
        assertBundleNames("", "zh", "zh_CN", "zh_CN_variant",
                LocaleUtil.calculateBundleNames("", new Locale("zh", "CN", "variant")));

        // hello.jsp
        assertBundleNames("hello.jsp", null, null, null, LocaleUtil.calculateBundleNames("hello.jsp", null));
        assertBundleNames("hello.jsp", null, null, null, LocaleUtil.calculateBundleNames("hello.jsp", new Locale("")));
        assertBundleNames("hello.jsp", "hello_zh.jsp", null, null,
                LocaleUtil.calculateBundleNames("hello.jsp", new Locale("zh")));
        assertBundleNames("hello.jsp", "hello_zh.jsp", "hello_zh_CN.jsp", null,
                LocaleUtil.calculateBundleNames("hello.jsp", new Locale("zh", "CN")));
        assertBundleNames("hello.jsp", "hello_zh.jsp", "hello_zh_CN.jsp", "hello_zh_CN_variant.jsp",
                LocaleUtil.calculateBundleNames("hello.jsp", new Locale("zh", "CN", "variant")));

        // hello.
        assertBundleNames("hello", null, null, null, LocaleUtil.calculateBundleNames("hello.", null));
        assertBundleNames("hello", null, null, null, LocaleUtil.calculateBundleNames("hello.", new Locale("")));
        assertBundleNames("hello", "hello_zh", null, null, LocaleUtil.calculateBundleNames("hello.", new Locale("zh")));
        assertBundleNames("hello", "hello_zh", "hello_zh_CN", null,
                LocaleUtil.calculateBundleNames("hello.", new Locale("zh", "CN")));
        assertBundleNames("hello", "hello_zh", "hello_zh_CN", "hello_zh_CN_variant",
                LocaleUtil.calculateBundleNames("hello.", new Locale("zh", "CN", "variant")));

        // hello.world
        assertBundleNames("hello.world", null, null, null, LocaleUtil.calculateBundleNames("hello.world", null));
        assertBundleNames("hello.world", null, null, null,
                LocaleUtil.calculateBundleNames("hello.world", new Locale(""), true));
        assertBundleNames("hello.world", "hello.world_zh", null, null,
                LocaleUtil.calculateBundleNames("hello.world", new Locale("zh"), true));
        assertBundleNames("hello.world", "hello.world_zh", "hello.world_zh_CN", null,
                LocaleUtil.calculateBundleNames("hello.world", new Locale("zh", "CN"), true));
        assertBundleNames("hello.world", "hello.world_zh", "hello.world_zh_CN", "hello.world_zh_CN_variant",
                LocaleUtil.calculateBundleNames("hello.world", new Locale("zh", "CN", "variant"), true));
    }

    private void assertBundleNames(String name1, String name2, String name3, String name4, List<String> names) {
        Collections.reverse(names);

        int length = names.size();

        if (name2 == null && name3 == null && name4 == null) {
            assertTrue(length == 1);
        } else if (name3 == null && name4 == null) {
            assertTrue(length == 2);
        } else if (name4 == null) {
            assertTrue(length == 3);
        } else {
            assertTrue(length == 4);
        }

        if (length > 0) {
            assertEquals(name1, names.get(0));
        }

        if (length > 1) {
            assertEquals(name2, names.get(1));
        }

        if (length > 2) {
            assertEquals(name3, names.get(2));
        }

        if (length > 3) {
            assertEquals(name4, names.get(3));
        }
    }

    @Test
    public void systemLocale() {
        assertLocaleInfo(systemLocale, systemCharset, LocaleUtil.getSystem());
    }

    @Test
    public void defaultLocale() {
        // 未设置
        assertLocaleInfo(systemLocale, systemCharset, LocaleUtil.getDefault());

        // 设置
        assertLocaleInfo(systemLocale, systemCharset, LocaleUtil.setDefault(new LocaleInfo(Locale.CHINA, "GBK")));
        assertLocaleInfo(Locale.CHINA, "GBK", LocaleUtil.getDefault());

        assertLocaleInfo(Locale.CHINA, "GBK", LocaleUtil.setDefault(Locale.CHINA));
        assertLocaleInfo(Locale.CHINA, "UTF-8", LocaleUtil.getDefault());

        assertLocaleInfo(Locale.CHINA, "UTF-8", LocaleUtil.setDefault(Locale.CHINA, "GB2312"));
        assertLocaleInfo(Locale.CHINA, "GB2312", LocaleUtil.getDefault());

        try {
            LocaleUtil.setDefault(Locale.CHINA, "invalid");
            fail();
        } catch (UnsupportedCharsetException e) {
            assertEquals("invalid", e.getCharsetName());
        }

        // 复位
        LocaleUtil.resetDefault();
        assertLocaleInfo(systemLocale, systemCharset, LocaleUtil.getDefault());
    }

    @Test
    public void contextLocale() {
        LocaleUtil.setDefault(Locale.US, "ISO-8859-1");

        // 未设置
        assertLocaleInfo(Locale.US, "ISO-8859-1", LocaleUtil.getContext());

        // 修改default，context默认值也被修改
        LocaleUtil.setDefault(Locale.CANADA, "UTF-8");
        assertLocaleInfo(Locale.CANADA, "UTF-8", LocaleUtil.getContext());
        LocaleUtil.setDefault(Locale.US, "ISO-8859-1");

        // 设置
        assertLocaleInfo(Locale.US, "ISO-8859-1", LocaleUtil.setContext(new LocaleInfo(Locale.CHINA, "GBK")));
        assertLocaleInfo(Locale.CHINA, "GBK", LocaleUtil.getContext());

        assertLocaleInfo(Locale.CHINA, "GBK", LocaleUtil.setContext(Locale.CHINA));
        assertLocaleInfo(Locale.CHINA, "UTF-8", LocaleUtil.getContext());

        assertLocaleInfo(Locale.CHINA, "UTF-8", LocaleUtil.setContext(Locale.CHINA, "GB2312"));
        assertLocaleInfo(Locale.CHINA, "GB2312", LocaleUtil.getContext());

        try {
            LocaleUtil.setContext(Locale.CHINA, "invalid");
            fail();
        } catch (UnsupportedCharsetException e) {
            assertEquals("invalid", e.getCharsetName());
        }

        // 复位
        LocaleUtil.resetContext();
        assertLocaleInfo(Locale.US, "ISO-8859-1", LocaleUtil.getContext());

        // 修改default，context默认值也被修改
        LocaleUtil.setDefault(Locale.CANADA, "UTF-8");
        assertLocaleInfo(Locale.CANADA, "UTF-8", LocaleUtil.getContext());
        LocaleUtil.setDefault(Locale.US, "ISO-8859-1");
    }

    private void assertLocaleInfo(Locale locale, String charset, LocaleInfo localeInfo) {
        assertEquals(locale, localeInfo.getLocale());
        assertEquals(Charset.forName(charset), localeInfo.getCharset());
    }

    @Test
    public void notifiers() {
        Notifier[] notifiers = TestUtil.getFieldValue(new LocaleUtil(), "notifiers", Notifier[].class);

        assertEquals(2, notifiers.length);

        N1 n1 = (N1) notifiers[0];
        N2 n2 = (N2) notifiers[1];

        // set default
        assertNull(n1.defaultValue);
        assertNull(n2.defaultValue);
        LocaleUtil.setDefault(Locale.US, "ISO-8859-1");
        assertEquals(new LocaleInfo(Locale.US, "ISO-8859-1"), n1.defaultValue);
        assertEquals(new LocaleInfo(Locale.US, "ISO-8859-1"), n2.defaultValue);

        // reset default
        LocaleUtil.resetDefault();
        assertNull(n1.defaultValue);
        assertNull(n2.defaultValue);

        // set context
        assertNull(n1.contextValue);
        assertNull(n2.contextValue);
        LocaleUtil.setContext(Locale.US, "ISO-8859-1");
        assertEquals(new LocaleInfo(Locale.US, "ISO-8859-1"), n1.contextValue);
        assertEquals(new LocaleInfo(Locale.US, "ISO-8859-1"), n2.contextValue);

        // reset context
        LocaleUtil.resetContext();
        assertNull(n1.contextValue);
        assertNull(n2.contextValue);
    }

    public static class N1 implements Notifier {
        LocaleInfo defaultValue;
        LocaleInfo contextValue;

        @Override
		public void defaultChanged(LocaleInfo newValue) {
            defaultValue = newValue;
        }

        @Override
		public void defaultReset() {
            defaultValue = null;
        }

        @Override
		public void contextChanged(LocaleInfo newValue) {
            contextValue = newValue;
        }

        @Override
		public void contextReset() {
            contextValue = null;
        }
    }

    public static class N2 extends N1 {
    }

}
