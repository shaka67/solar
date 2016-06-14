/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n;

import static com.ouer.solar.test.TestUtil.exception;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.ouer.solar.i18n.LocaleInfo.UnknownCharset;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:57:04
 */
public class UnknownCharsetTests {
    private UnknownCharset charset;

    @Test
    public void constructor() {
        try {
            new UnknownCharset(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e, exception("charset name"));
        }
    }

    @Test
    public void name() {
        charset = new UnknownCharset("test");
        assertEquals("test", charset.name());
    }

    @Test
    public void toString_() {
        charset = new UnknownCharset("test");
        assertEquals("test", charset.name());
    }

    @Test
    public void newEncoder() {
        charset = new UnknownCharset("test");

        try {
            charset.newEncoder();
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(e, exception("Could not create encoder for unknown charset: test"));
        }
    }

    @Test
    public void newDecoder() {
        charset = new UnknownCharset("test");

        try {
            charset.newDecoder();
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(e, exception("Could not create decoder for unknown charset: test"));
        }
    }
}
