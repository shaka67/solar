/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import com.ouer.solar.annotation.JMX;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.config.sync.ConfigSyncableLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class RabbitMQWorkerClientLifecycle extends ConfigSyncableLifecycle<RabbitMQConfig> {

	private static final String CONFIG_TYPE = "rabbitmq";

	private final RabbitMQWorkerClientManager manager;

	public RabbitMQWorkerClientLifecycle(RabbitMQConfigLocator locator,
	   		  								  ConfigSyncSubscriber subscriber,
	   		  								  RabbitMQWorkerClientManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) throws Exception {
		manager.getWorkerClient(appId);
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeWorkerClient(appId);
	}

}
