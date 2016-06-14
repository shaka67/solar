/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.google.common.collect.Sets;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.jobclient.JobSchedulerClient;
import com.ouer.solar.jobclient.spring.JobSchedulerClientConfiguration;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerInterface;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerServerConfiguration;
import com.ouer.solar.remote.RemoteClassDefinitions;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.common.TransportSpringConfig;
import com.ouer.solar.search.dal.dynamic.SearchDalManager;
import com.ouer.solar.search.dal.spring.SearchDalSpringConfig;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.definition.DefinitionSpringConfig;
import com.ouer.solar.search.ibs.IndexBuildConfig;
import com.ouer.solar.search.ibs.IndexBuilderTask;
import com.ouer.solar.search.ibs.dynamic.IbsLifecycle;
import com.ouer.solar.search.ibs.dynamic.IndexBuilderManager;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({SearchDalSpringConfig.class,
		   DefinitionSpringConfig.class,
		   TransportSpringConfig.class,
		   RabbitMQRemoteWorkerServerConfiguration.class,
		   DynamicRabbitMQConfiguration.class,
		   JobSchedulerClientConfiguration.class,
		   JmxSpringConfig.class,
		   IbsSyncConfig.class})
@ImportResource("classpath:/META-INF/applicationContext-search-ibs.xml")
public class IbsSpringConfig {

	@Autowired
	@Bean
	IndexBuilderManager indexBuilderManager(IndexBuildConfig config,
			  									 SearchConfigLocator locator,
			  									 TransportClientManager clientManager,
			  									 DefinitionFactoryManager definitionManager,
			  									 SearchDalManager dalManager,
			  									 JobSchedulerClient jobScheduler) {
		return new IndexBuilderManager(config,
										   locator,
										   clientManager,
										   definitionManager,
										   dalManager,
										   jobScheduler);
	}

	@Bean(name = RabbitMQRemoteWorkerInterface.QUALIFIER_NAME)
	RemoteClassDefinitions remoteClassDefinitions() {
		final Set<Class<?>> remoteClasses = Sets.newHashSet();
		remoteClasses.add(IndexBuilderTask.class);
		return new RemoteClassDefinitions(remoteClasses);
	}

	@Autowired
	@Bean
	IndexBuilderTask indexBuilderTask(IndexBuilderManager manager) {
		return new IndexBuilderTask(manager);
	}

	@Autowired
	@Bean
	IbsLifecycle ibsLifecycle(SearchConfigLocator locator,
							   ConfigSyncSubscriber subscriber,
							   IndexBuilderManager manager) {
		return new IbsLifecycle(locator, subscriber, manager);
	}
}
