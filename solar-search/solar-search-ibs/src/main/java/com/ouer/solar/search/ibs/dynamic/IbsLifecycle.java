/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs.dynamic;

import com.ouer.solar.annotation.JMX;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.config.sync.ConfigSyncableLifecycle;
import com.ouer.solar.search.ibs.IndexBuildProxy;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class IbsLifecycle extends ConfigSyncableLifecycle<SearchConfig> {

	private static final String CONFIG_TYPE = "ibs";

	private final IndexBuilderManager manager;

	public IbsLifecycle(SearchConfigLocator locator,
			   			 ConfigSyncSubscriber subscriber,
			   			 IndexBuilderManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	public void buildIndex(String appId, int indexId) throws Exception {
		((IndexBuildProxy) manager.getIndexBuildService(appId)).buildIndex(indexId);
	}

	public void deleteIndex(String appId, String index) {
		((IndexBuildProxy) manager.getIndexBuildService(appId)).deleteIndex(index);
	}

	public void buildMapping(String appId, int type) throws Exception {
		((IndexBuildProxy) manager.getIndexBuildService(appId)).buildMapping(type);
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) throws Exception {
		manager.getIndexBuildService(appId);
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeIndexBuildService(appId);
	}

	public void rebuild(String appId) {

	}
}
