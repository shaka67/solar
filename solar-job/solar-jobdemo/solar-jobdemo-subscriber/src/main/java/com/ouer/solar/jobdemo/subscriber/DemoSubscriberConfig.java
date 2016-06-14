/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobdemo.subscriber;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Sets;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerInterface;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerServerConfiguration;
import com.ouer.solar.remote.RemoteClassDefinitions;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ RabbitMQRemoteWorkerServerConfiguration.class,
			DynamicRabbitMQConfiguration.class,
			ConfigSyncSpring.class,
			ConfigDBLocatorSpring.class,
			ConfigDalStoreSpring.class,
			ProfileSpringConfig.class })
public class DemoSubscriberConfig {

	@Bean(name = RabbitMQRemoteWorkerInterface.QUALIFIER_NAME)
	RemoteClassDefinitions remoteClassDefinitions() {
		final Set<Class<?>> remoteClasses = Sets.newHashSet();
		remoteClasses.add(DemoWorkflow.class);
		return new RemoteClassDefinitions(remoteClasses);
	}

	@Bean
	DemoWorkflow demoWorkflow() {
		return new DemoWorkflow();
	}
}
