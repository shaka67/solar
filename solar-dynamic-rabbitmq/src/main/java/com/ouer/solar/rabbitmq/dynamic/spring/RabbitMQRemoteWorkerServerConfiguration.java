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
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerLifecycle;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerManager;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerInterface;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerRegister;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;
import com.ouer.solar.remote.RemoteClassDefinitions;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class RabbitMQRemoteWorkerServerConfiguration {

	@Autowired
	@Bean
	RabbitMQRemoteWorkerRegister rabbitMQRemoteWorkerRegister
		(@Qualifier(RabbitMQRemoteWorkerInterface.QUALIFIER_NAME) RemoteClassDefinitions definitions) {
		return new RabbitMQRemoteWorkerRegister(definitions);
	}

	@Autowired
	@Bean(name = "rabbitMQWorkerServerManager")
	RabbitMQWorkerServerManager rabbitMQWorkerServerManager(RabbitMQConfigLocator locator,
																		 MessageConverter converter,
																		 NoDirectDependentCarrierConverter nddCarrierConverter,
																		 RabbitMQRemoteWorkerRegister workerRegister) {
		return new RabbitMQWorkerServerManager(locator, converter, nddCarrierConverter, workerRegister);
	}

	@Autowired
	@Bean
	RabbitMQWorkerServerLifecycle rabbitMQWorkerServerLifecycle(RabbitMQConfigLocator locator,
			   											   				   ConfigSyncSubscriber subscriber,
			   											   				   @Qualifier("rabbitMQWorkerServerManager")
			   											   				   RabbitMQWorkerServerManager manager) {
		return new RabbitMQWorkerServerLifecycle(locator, subscriber, manager);
	}

}
