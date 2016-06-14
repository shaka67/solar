/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ConfigSyncEvent {

	private final String appId;
	private final String configType;

	public ConfigSyncEvent(String appId, String configType) {
		this.appId = appId;
		this.configType = configType;
	}

	public String getAppId() {
		return appId;
	}

	public String getConfigType() {
		return configType;
	}

	@Override
	public String toString() {
		return "ConfigSyncEvent [appId=" + appId + ", configType=" + configType
				+ "]";
	}

}
