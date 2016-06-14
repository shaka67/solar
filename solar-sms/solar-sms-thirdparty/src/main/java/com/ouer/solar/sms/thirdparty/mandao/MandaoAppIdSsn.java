/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.thirdparty.mandao;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MandaoAppIdSsn {

	private final String appId;
	private final String ssn;

	public MandaoAppIdSsn(String appId, String ssn) {
		this.appId = appId;
		this.ssn = ssn;
	}

	public String getAppId() {
		return appId;
	}

	public String getSsn() {
		return ssn;
	}

}
