/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.jobserver.dynamic.JobServerLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobserverSyncListener implements BusSignalListener<ConfigSyncEvent> {

	private static final Log LOG = LogFactory.getLog(JobserverSyncListener.class);

	private final JobServerLifecycle lifecycle;

	public JobserverSyncListener(BusSignalManager bsm, JobServerLifecycle lifecycle) {
		bsm.bind(ConfigSyncEvent.class, this);
		this.lifecycle = lifecycle;
	}

	@Override
	public void signalFired(ConfigSyncEvent signal) {
		final String appId = signal.getAppId();
		try {
			lifecycle.reload(appId);
		} catch (final Exception e) {
			LOG.error("", e);
		}
	}

}
