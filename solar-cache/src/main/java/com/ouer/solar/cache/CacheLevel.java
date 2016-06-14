/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum CacheLevel {

	LOCAL(1, "local"),
	SHARED(2, "shared"),
    ;

	private final int code;
    private final String desc;

    CacheLevel(int code, String desc) {
    	this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public static CacheLevel fromCode(byte code) {
        for (final CacheLevel type : CacheLevel.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc();
    }
}
