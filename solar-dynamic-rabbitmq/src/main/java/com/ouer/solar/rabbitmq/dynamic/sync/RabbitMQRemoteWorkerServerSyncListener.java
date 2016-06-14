/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerServerSyncListener implements BusSignalListener<ConfigSyncEvent> {

	private static final Log LOG = LogFactory.getLog(RabbitMQRemoteWorkerServerSyncListener.class);

	private final RabbitMQWorkerServerLifecycle lifecycle;

	public RabbitMQRemoteWorkerServerSyncListener(RabbitMQWorkerServerLifecycle lifecycle) {
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
