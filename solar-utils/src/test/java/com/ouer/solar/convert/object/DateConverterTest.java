/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.ouer.solar.convert.object.DateConverter;
import com.ouer.solar.date.DatetimeObject;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:24:45
 */
public class DateConverterTest {

    private static long time = new DatetimeObject(2011, 11, 1, 9, 10, 12, 567).getTimeInMillis();

    DateConverter dateConverter = new DateConverter();

    @Test
    public void testCalendar2Date() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date date = dateConverter.toConvert(calendar);
        assertEquals(time, date.getTime());
    }

    @Test
    public void testDate2Date() {
        Date date2 = new Date(time);
        Date date = dateConverter.toConvert(date2);
        assertEquals(time, date.getTime());
    }

    @Test
    public void testTimestamp2Date() {
        Timestamp timestamp = new Timestamp(time);
        Date date = dateConverter.toConvert(timestamp);
        assertEquals(time, date.getTime());
    }

    @Test
    public void testSqlDate2Date() {
        java.sql.Date date2 = new java.sql.Date(time);
        Date date = dateConverter.toConvert(date2);
        assertEquals(time, date.getTime());
    }

    @Test
    public void testSqlTime2Date() {
        Time sqltime = new Time(time);
        Date date = dateConverter.toConvert(sqltime);
        assertEquals(time, date.getTime());
    }

    @Test
    public void testJDateTime2Date() {
        DatetimeObject datetime = new DatetimeObject(time);
        Date date = dateConverter.toConvert(datetime);
        assertEquals(time, date.getTime());
    }

    @Test
    @SuppressWarnings({ "deprecation" })
    public void toConvert() {

        assertEquals(new Date(885858), dateConverter.toConvert("885858"));
        assertEquals(new Date(123), dateConverter.toConvert(Integer.valueOf(123)));

        Date date = new Date(111, 0, 1);
        assertEquals(date, dateConverter.toConvert("2011-01-01"));

        date = new Date(111, 0, 1, 10, 59, 55);
        assertEquals(date, dateConverter.toConvert("2011-01-01 10:59:55"));
    }
}
