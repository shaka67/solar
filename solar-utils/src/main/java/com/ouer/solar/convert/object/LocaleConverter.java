/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.util.Locale;

import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.i18n.LocaleUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午4:18:05
 */
public class LocaleConverter extends ObjectConverter<Locale> implements TypeConverter<Locale> {

    public LocaleConverter() {
        register(Locale.class);
    }

    @Override
    public Locale toConvert(String value) {
        return LocaleUtil.parseLocale(value);
    }

    @Override
    public String fromConvert(Locale value) {
        return String.valueOf(value);
    }

    @Override
	public Locale toConvert(Object value) {
        if (value.getClass() == Locale.class) {
            return (Locale) value;
        }

        return LocaleUtil.parseLocale(value.toString());
    }

}
