/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.enums.internal;

/**
 * 基于整数的数字接口.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午2:52:11
 */

public interface NumberType {
    /** 十六进制的基数：16。 */
    int RADIX_HEX = 16;

    /** 八进制的基数：8。 */
    int RADIX_OCT = 8;

    /** 二进制的基数：2。 */
    int RADIX_BIN = 2;

    /**
     * 取得整数值
     * 
     * @return 整数值
     */
    int intValue();

    /**
     * 取得长整数值
     * 
     * @return 长整数值
     */
    long longValue();

    /**
     * 取得浮点值
     * 
     * @return 浮点值
     */
    float floatValue();

    /**
     * 取得取得double值
     * 
     * @return double值
     */
    double doubleValue();

    /**
     * 取得byte值
     * 
     * @return byte值
     */
    byte byteValue();

    /**
     * 取得short值
     * 
     * @return short值
     */
    short shortValue();

    /**
     * 转换成十六进制整数字符串.
     * 
     * @return 十六进制整数字符串
     */
    String toHexString();

    /**
     * 转换成八进制整数字符串.
     * 
     * @return 八进制整数字符串
     */
    String toOctalString();

    /**
     * 转换成二进制整数字符串.
     * 
     * @return 二进制整数字符串
     */
    String toBinaryString();
}
