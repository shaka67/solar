/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.spring;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientLifecycle;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientManager;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class RabbitMQRemoteWorkerClientConfiguration {

	@Autowired
	@Bean(name = "rabbitMQWorkerClientManager")
	RabbitMQWorkerClientManager rabbitMQWorkerClientManager(RabbitMQConfigLocator locator,
	  		 													MessageConverter converter,
	  		 													NoDirectDependentCarrierConverter nddCarrierConverter) {
		return new RabbitMQWorkerClientManager(locator, converter, nddCarrierConverter);
	}

	@Autowired
	@Bean(name = "rabbitMQWorkerClientLifecycle")
	RabbitMQWorkerClientLifecycle rabbitMQWorkerClientLifecycle(RabbitMQConfigLocator locator,
				   														 ConfigSyncSubscriber subscriber,
				   														 @Qualifier("rabbitMQWorkerClientManager")
																		 RabbitMQWorkerClientManager manager) {
		return new RabbitMQWorkerClientLifecycle(locator, subscriber, manager);
	}

}
