/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.dynamic;

import com.ouer.solar.annotation.JMX;
import com.ouer.solar.config.jobserver.JobServerConfigLocator;
import com.ouer.solar.config.jobserver.JobServerSpecialConfig;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.config.sync.ConfigSyncableLifecycle;
import com.ouer.solar.jobserver.daemon.JobQueueDaemon;
import com.ouer.solar.jobserver.daemon.JobSchedulerDaemon;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class JobServerLifecycle extends ConfigSyncableLifecycle<JobServerSpecialConfig> {

	private static final String CONFIG_TYPE = "jobserver";

	private final JobServerQueueManager manager;

	public JobServerLifecycle(JobServerConfigLocator locator,
								ConfigSyncSubscriber subscriber,
								JobServerQueueManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) {
		final JobSchedulerDaemon schedulerDaemon = manager.getJobSchedulerDaemon(appId);
		if (schedulerDaemon != null) {
			schedulerDaemon.start();
		}
		final JobQueueDaemon subscriberDaemon = manager.getJobQueueDaemon(appId);
		if (subscriberDaemon != null) {
			subscriberDaemon.start();
		}
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeJobSchedulerDaemon(appId);
		manager.removeJobQueueDaemon(appId);
	}

}
