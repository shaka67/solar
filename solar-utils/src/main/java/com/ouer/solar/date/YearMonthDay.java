/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.date;

import java.util.Calendar;
import java.util.Date;

import com.ouer.solar.Assert;
import com.ouer.solar.DateUtil;
import com.ouer.solar.lang.StringableSupport;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月5日 上午3:39:53
 */
public class YearMonthDay extends StringableSupport {

    private static final char complementalCode = '0';

    private int year;

    private int month;

    private int day;

    public YearMonthDay() {

    }

    public YearMonthDay(int year) {
        this.year = year;
    }

    public YearMonthDay(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public YearMonthDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static YearMonthDay fromDate() {
        Calendar cal = Calendar.getInstance();

        YearMonthDay readableDate =
                new YearMonthDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

        return readableDate;
    }

    public static YearMonthDay fromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        YearMonthDay readableDate =
                new YearMonthDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

        return readableDate;
    }

    public static Date toDate(YearMonthDay readableDate) {
        Assert.assertTrue(readableDate.month > 0 && readableDate.month <= 12, "month must between 1-12");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, readableDate.month - 1);

        int dayCeil = DateUtil.getLastDayOfMonth(cal);
        Assert.assertTrue(readableDate.day > 0 && readableDate.day <= dayCeil, "day must between 0-{}", dayCeil);
        String sDate = readableDate.year + "";

        sDate = (readableDate.month < 10) ? sDate + complementalCode + readableDate.month : sDate + readableDate.month;

        sDate = (readableDate.day < 10) ? sDate + complementalCode + readableDate.day : sDate + readableDate.day;

        return DateUtil.parseDate(sDate);
    }

    public Date toDate() {
        return toDate(this);
    }

    public int getYear() {
        return year;
    }

    public YearMonthDay setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public YearMonthDay setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDay() {
        return day;
    }

    public YearMonthDay setDay(int day) {
        this.day = day;
        return this;
    }

}
