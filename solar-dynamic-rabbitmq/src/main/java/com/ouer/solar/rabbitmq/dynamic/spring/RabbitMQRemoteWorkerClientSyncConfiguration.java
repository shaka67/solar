/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientLifecycle;
import com.ouer.solar.rabbitmq.dynamic.sync.RabbitMQRemoteWorkerClientSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class RabbitMQRemoteWorkerClientSyncConfiguration {

	@Autowired
	@Bean
	RabbitMQRemoteWorkerClientSyncListener rabbitMQRemoteWorkerClientSyncListener(BusSignalManager bsm,
																								RabbitMQWorkerClientLifecycle lifecycle) {
		final RabbitMQRemoteWorkerClientSyncListener listener = new RabbitMQRemoteWorkerClientSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}
}
