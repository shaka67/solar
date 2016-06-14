/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.util.Calendar;
import java.util.Date;

import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午2:52:48
 */
public class CalendarConverter extends ObjectConverter<Calendar> implements TypeConverter<Calendar> {

    public CalendarConverter() {
        register(Calendar.class);
    }

    @Override
    public Calendar toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Calendar value) {
        return String.valueOf(value);
    }

    @Override
	public Calendar toConvert(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return (Calendar) value;
        }
        if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            return calendar;
        }

        if (value instanceof Number) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(((Number) value).longValue());
            return calendar;
        }

        String stringValue = value.toString().trim();

        return convert(stringValue);
    }

    private Calendar convert(String value) {
        try {
            long milliseconds = Long.parseLong(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar;
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
