/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.quartz.spi.JobFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ouer.solar.config.jobserver.JobServerConfigLocator;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.jobserver.dynamic.JobServerLifecycle;
import com.ouer.solar.jobserver.dynamic.JobServerQueueManager;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ JobRabbitMQWorkflowConfiguration.class,
			JobSchedulerConfiguration.class,
			ConfigDBLocatorSpring.class,
			ConfigDalStoreSpring.class,
			JmxSpringConfig.class,
			JobSyncConfiguration.class})
@ImportResource("classpath:/META-INF/applicationContext-jobserver.xml")
public class JobServerConfiguration {

	@Autowired
	@Bean
	JobServerQueueManager jobServerQueueManager(RabbitMQConfigLocator locator,
			  											 MessageConverter converter,
			  											 NoDirectDependentCarrierConverter nddCarrierConverter,
			  											 JobServerConfigLocator jobLocator,
			  											 JobFactory jobFactory) {
		return new JobServerQueueManager(locator, converter, nddCarrierConverter, jobLocator, jobFactory);
	}

	@Autowired
	@Bean
	JobServerLifecycle jobServerLifecycle(JobServerConfigLocator locator,
											  ConfigSyncSubscriber subscriber,
											  JobServerQueueManager manager) {
		return new JobServerLifecycle(locator, subscriber, manager);
	}

}
