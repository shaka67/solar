/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BaseConfig implements AppConfigurable {

	protected String appId;

	public BaseConfig() {}

	public BaseConfig(String appId) {
		this.appId = appId;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
