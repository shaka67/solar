/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.api;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public enum ThirdSmsSystemEnum {
	MANDAO("0","漫道");
	
	private String code;
	
	private String desc;
	
	ThirdSmsSystemEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	

}
