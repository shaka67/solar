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
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerLifecycle;
import com.ouer.solar.rabbitmq.dynamic.sync.RabbitMQRemoteWorkerServerSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class RabbitMQRemoteWorkerServerSyncConfiguration {

	@Autowired
	@Bean
	RabbitMQRemoteWorkerServerSyncListener rabbitMQRemoteWorkerServerSyncListener(BusSignalManager bsm,
																								  RabbitMQWorkerServerLifecycle lifecycle) {
		final RabbitMQRemoteWorkerServerSyncListener listener = new RabbitMQRemoteWorkerServerSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}
}
