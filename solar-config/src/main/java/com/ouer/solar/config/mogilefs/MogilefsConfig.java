/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs;

import com.ouer.solar.config.BaseConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogilefsConfig extends BaseConfig {

	private String trackers;
	private int reconnectTimeout;
	private int maxActive;
	private int maxIdle;
	private String domain;
	private String httpPrefix;

	public String getTrackers() {
		return trackers;
	}

	public void setTrackers(String trackers) {
		this.trackers = trackers;
	}

	public int getReconnectTimeout() {
		return reconnectTimeout;
	}

	public void setReconnectTimeout(int reconnectTimeout) {
		this.reconnectTimeout = reconnectTimeout;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHttpPrefix() {
		return httpPrefix;
	}

	public void setHttpPrefix(String httpPrefix) {
		this.httpPrefix = httpPrefix;
	}

}
