/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.remote.RemoteMethod;
import com.ouer.solar.remote.RemoteWorker;
import com.ouer.solar.search.ibs.dynamic.IndexBuilderManager;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@RemoteWorker(group = "solar.daily.ibs.build")
public class IndexBuilderTask {

	private final static Logger LOG = LoggerFactory.getLogger(IndexBuilderTask.class);

	private final IndexBuilderManager manager;

	public IndexBuilderTask(IndexBuilderManager manager) {
		this.manager = manager;
	}

	@RemoteMethod(name = "rebuild")
	public void rebuildIndex(String appId) throws Exception {
		if (LOG.isInfoEnabled()) {
			LOG.info("start to execute daily rebuild index task...");
		}

		manager.getIndexBuildService(appId).buildIndex();

		if (LOG.isInfoEnabled()) {
			LOG.info("end to execute daily rebuild index task");
		}
	}
}
