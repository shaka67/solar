/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.jobdemo.subscriber;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.jobdemo.model.DemoModel;
import com.ouer.solar.remote.RemoteMethod;
import com.ouer.solar.remote.RemoteWorker;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@RemoteWorker(group="jobdemo.workflow")
public class DemoWorkflow {

	private static final Logger LOG = LoggerFactory.getLogger(DemoWorkflow.class);

	@RemoteMethod(name = "on.scheduled.model")
	public void onScheduled(DemoModel model) {
		LOG.info("start to trigger [on.scheduled.model] ...");
		System.err.println(model);
		LOG.info("scheduled [DemoWorkflow] successfully.");
	}

	@RemoteMethod(name = "on.scheduled.string.and.map")
	public void onScheduled(int id, String string, Map<Integer, String> map) {
		LOG.info("start to trigger [on.scheduled.string.and.map] ...");
		System.err.println(id);
		System.err.println(string);
		System.err.println(map);
		LOG.info("scheduled [DemoWorkflow] successfully.");
	}

	@RemoteMethod(name = "on.scheduled.models")
	public void onScheduled(List<DemoModel> models) {
		LOG.info("start to trigger [on.scheduled.models] ...");
		System.err.println(models);
		LOG.info("scheduled [DemoWorkflow] successfully.");
	}
}
