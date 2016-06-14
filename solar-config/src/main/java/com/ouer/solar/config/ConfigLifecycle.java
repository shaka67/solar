/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import java.util.List;

import com.ouer.solar.able.Startable;
import com.ouer.solar.able.Stopable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class ConfigLifecycle<T extends BaseConfig> implements Startable, Stopable, ConfigReloadable {

	protected final ConfigLocator<? extends BaseConfig> locator;

	public ConfigLifecycle(ConfigLocator<? extends BaseConfig> locator) {
		this.locator = locator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void start() throws Exception {
		final List<T> configs = (List<T>) locator.getConfigs();
		for (final BaseConfig config : configs) {
			start(config.getAppId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void stop() throws Exception {
		final List<T> configs = (List<T>) locator.getConfigs();
		for (final BaseConfig config : configs) {
			stop(config.getAppId());
		}
	}

	@Override
	public synchronized void reload(String appId) throws Exception {
		stop(appId);
		start(appId);
	}

	protected abstract void start(String appId) throws Exception;
	protected abstract void stop(String appId) throws Exception;
}
