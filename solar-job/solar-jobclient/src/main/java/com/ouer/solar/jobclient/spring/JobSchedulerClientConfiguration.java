/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.jobclient.JobScheduler;
import com.ouer.solar.jobclient.JobSchedulerClient;
import com.ouer.solar.jobclient.proxy.RabbitMQJobSchedulerProxy;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQManager;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ DynamicRabbitMQConfiguration.class,
			ConfigDBLocatorSpring.class,
			ConfigDalStoreSpring.class })
public class JobSchedulerClientConfiguration {

	@Autowired
	@Bean
	JobSchedulerClient jobSchedulerClient(JobScheduler scheduler) {
		return new JobSchedulerClient(scheduler);
	}

	@Autowired
	@Bean
	JobScheduler JobScheduler(@Qualifier("rabbitMQManager") RabbitMQManager manager) {
		return new RabbitMQJobSchedulerProxy(manager);
	}
}
