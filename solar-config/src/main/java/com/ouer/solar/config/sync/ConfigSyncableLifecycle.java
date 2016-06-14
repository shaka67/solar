/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import java.util.List;

import com.ouer.solar.config.BaseConfig;
import com.ouer.solar.config.ConfigLifecycle;
import com.ouer.solar.config.ConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class ConfigSyncableLifecycle<T extends BaseConfig> extends ConfigLifecycle<T> implements ConfigSyncBinder {

	protected final ConfigSyncSubscriber subscriber;

	public ConfigSyncableLifecycle(ConfigLocator<T> locator,
									  ConfigSyncSubscriber subscriber) {
		super(locator);
		this.subscriber = subscriber;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {
		final List<T> configs = (List<T>) locator.getConfigs();
		for (final BaseConfig config : configs) {
			start(config.getAppId());
			subscriber.bind(config.getAppId(), getConfigType());
		}
	}

}
