/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss.dynamic;

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
public class RssLifecycle extends ConfigSyncableLifecycle<SearchConfig> {

	private static final String CONFIG_TYPE = "rss";

	private final RealtimeSearchManager manager;

	public RssLifecycle(SearchConfigLocator locator,
	 			 		ConfigSyncSubscriber subscriber,
	 			 		RealtimeSearchManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) throws Exception {
		manager.getRealtimeSearch(appId);
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeRealtimeSearch(appId);
	}
}
