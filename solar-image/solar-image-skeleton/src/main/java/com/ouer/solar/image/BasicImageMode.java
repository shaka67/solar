/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

/**
 * Common image query mode for all most image server like with
 * minimum width or minimum height
 * minimum width or minimum height and cut
 * maximum width or maximum height
 * and so on
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum BasicImageMode {

	MIN_WH_CUT(1, "min-cut"),
    MAX_WH(2, "max"),
    MIN_WH(3, "min")
    ;

    private final int code;
    private final String type;

    BasicImageMode(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int code() {
        return code;
    }

    public String type() {
        return type;
    }

    public static BasicImageMode fromType(String type) {
        for (final BasicImageMode mode : BasicImageMode.values()) {
            if (mode.type().equals(type)) {
                return mode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type();
    }
}
