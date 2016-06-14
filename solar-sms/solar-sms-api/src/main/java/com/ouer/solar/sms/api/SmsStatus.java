/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.api;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum SmsStatus {

	SUCCESS(1, "success"),
	FAILURE(2,"failure"),
	PENDING(3,"pending")
    ;
	
	private final int code;
    private final String desc;

    SmsStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
    }
	
	public int code() {
		return code;
	}

    public String desc() {
        return desc;
    }

    public static SmsStatus fromCode(int code) {
        for (final SmsStatus status : SmsStatus.values()) {
            if (status.code() == code) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return desc();
    }
}
