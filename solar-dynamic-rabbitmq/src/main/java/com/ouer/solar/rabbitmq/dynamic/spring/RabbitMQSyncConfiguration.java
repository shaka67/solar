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
import com.ouer.solar.rabbitmq.dynamic.RabbitMQLifecycle;
import com.ouer.solar.rabbitmq.dynamic.sync.RabbitMQSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class RabbitMQSyncConfiguration {

	@Autowired
	@Bean
	RabbitMQSyncListener jobserverSyncListener(BusSignalManager bsm, RabbitMQLifecycle lifecycle) {
		final RabbitMQSyncListener listener = new RabbitMQSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}
}
