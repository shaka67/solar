/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 * 字节单位大小计算
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月15日 下午3:07:41
 */
public abstract class Sizing {

    private static final int KILOBYTE_UNIT = 1024;

    public static int GB(double giga) {
        return (int) giga * KILOBYTE_UNIT * KILOBYTE_UNIT * KILOBYTE_UNIT;
    }

    public static int MB(double mega) {
        return (int) mega * KILOBYTE_UNIT * KILOBYTE_UNIT;
    }

    public static int KB(double kilo) {
        return (int) kilo * KILOBYTE_UNIT;
    }

    public static int unlimited() {
        return -1;
    }

    public static String inKB(long bytes) {
        return String.format("%(,.1fKB", (double) bytes / KILOBYTE_UNIT);
    }

    public static String inMB(long bytes) {
        return String.format("%(,.1fMB", (double) bytes / KILOBYTE_UNIT / KILOBYTE_UNIT);
    }

    public static String inGB(long bytes) {
        return String.format("%(,.1fGB", (double) bytes / KILOBYTE_UNIT / KILOBYTE_UNIT / KILOBYTE_UNIT);
    }

}
