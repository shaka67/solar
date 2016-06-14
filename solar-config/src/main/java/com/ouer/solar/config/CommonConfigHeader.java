/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CommonConfigHeader implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

//	private String appId;
	private String configType;
	private String thirdparty;

	public CommonConfigHeader() {}

	public CommonConfigHeader(String configType, String thirdparty) {
//		this.appId = appId;
		this.configType = configType;
		this.thirdparty = thirdparty;
	}

//	public String getAppId() {
//		return appId;
//	}
//
//	public void setAppId(String appId) {
//		this.appId = appId;
//	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getThirdparty() {
		return thirdparty;
	}

	public void setThirdparty(String thirdparty) {
		this.thirdparty = thirdparty;
	}

	@Override
	public String toString() {
		return "CommonConfigHeader [configType=" + configType + ", thirdparty="
				+ thirdparty + "]";
	}

}
