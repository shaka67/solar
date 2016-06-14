/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import java.io.Serializable;
import java.util.Map;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DynamicConfig extends CommonConfigHeader implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	private Map<String, Object> configs;

	public DynamicConfig() {}

	public DynamicConfig(String appId, String configType, String thirdparty) {
		super(configType, thirdparty);
		this.appId = appId;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Object getConfig(String key) {
		return configs.get(key);
	}

	public Map<String, Object> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, Object> configs) {
		this.configs = configs;
	}

	@Override
	public String toString() {
		return "DynamicConfig [configs=" + configs + ", appId="
				+ getAppId() + ", configType=" + getConfigType()
				+ ", thirdparty=" + getThirdparty() + "]";
	}


}
