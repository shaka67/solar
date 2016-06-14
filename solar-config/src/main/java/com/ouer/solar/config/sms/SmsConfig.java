/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms;

import com.ouer.solar.config.DataSourceConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsConfig extends DataSourceConfig {

	// database
//	private String dalConfigFile;

	private String smsServiceObjects;

	public String getSmsServiceObjects() {
		return smsServiceObjects;
	}

	public void setSmsServiceObjects(String smsServiceObjects) {
		this.smsServiceObjects = smsServiceObjects;
	}

}
