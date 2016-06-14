/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerClientSyncListener implements BusSignalListener<ConfigSyncEvent> {

	private static final Log LOG = LogFactory.getLog(RabbitMQRemoteWorkerClientSyncListener.class);

	private final RabbitMQWorkerClientLifecycle lifecycle;

	public RabbitMQRemoteWorkerClientSyncListener(RabbitMQWorkerClientLifecycle lifecycle) {
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
