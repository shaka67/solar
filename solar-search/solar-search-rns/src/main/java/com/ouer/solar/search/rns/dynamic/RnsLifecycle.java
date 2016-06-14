/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns.dynamic;

import com.ouer.solar.annotation.JMX;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.config.sync.ConfigSyncableLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class RnsLifecycle extends ConfigSyncableLifecycle<SearchConfig> {

	private static final String CONFIG_TYPE = "rns";

	private final RealtimeUpdateManager manager;

	public RnsLifecycle(SearchConfigLocator locator,
  			 			 ConfigSyncSubscriber subscriber,
  			 			 RealtimeUpdateManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) throws Exception {
		manager.getRealtimeUpdateClient(appId);
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeRealtimeUpdateClient(appId);
	}

}
