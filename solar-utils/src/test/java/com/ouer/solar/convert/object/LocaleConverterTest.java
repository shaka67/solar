/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.ouer.solar.convert.object.LocaleConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:45:21
 */
public class LocaleConverterTest {

    @Test
    public void toConvert() {
        LocaleConverter localeConverter = new LocaleConverter();

        assertEquals(new Locale("en"), localeConverter.toConvert("en"));
        assertEquals(new Locale("en", "US"), localeConverter.toConvert("en_US"));
        assertEquals(new Locale("en", "US", "win"), localeConverter.toConvert("en_US_win"));

        assertEquals(new Locale("en"), localeConverter.toConvert(new Locale("en")));

    }
}
