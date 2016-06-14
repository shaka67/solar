/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum CacheSignalOperator {

	EVICT((byte) 0x01, "evict"),
	CLEAR((byte) 0x02, "clear"),
    ;

	private final byte code;
    private final String desc;

    CacheSignalOperator(byte code, String desc) {
    	this.code = code;
        this.desc = desc;
    }

    public byte code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public static CacheSignalOperator fromCode(byte code) {
        for (final CacheSignalOperator operator : CacheSignalOperator.values()) {
            if (operator.code == code) {
                return operator;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc();
    }
}
