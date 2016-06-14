/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.util.Calendar;
import java.util.Date;

import com.ouer.solar.StringUtil;
import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.date.DatetimeObject;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午3:19:44
 */
public class DateConverter extends ObjectConverter<Date> implements TypeConverter<Date> {

    @Override
    public Date toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(Date value) {
        return String.valueOf(value);
    }

    @Override
	public Date toConvert(Object value) {
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Calendar) {
            return new Date(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof DatetimeObject) {
            return ((DatetimeObject) value).convertToDate();
        }
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }

        return convert(value.toString());

    }

    private Date convert(String value) {
        String stringValue = value.trim();

        if (!StringUtil.isDigits(stringValue)) {
            DatetimeObject datetime = new DatetimeObject(stringValue, DatetimeObject.DEFAULT_FORMAT);
            return datetime.convertToDate();
        }
        try {
            long milliseconds = Long.parseLong(stringValue);
            return new Date(milliseconds);
        } catch (NumberFormatException e) {
            throw new ConvertException(value, e);
        }
    }

}
