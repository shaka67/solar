/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.common.TransportSpringConfig;
import com.ouer.solar.search.dal.dynamic.SearchDalManager;
import com.ouer.solar.search.dal.spring.SearchDalSpringConfig;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.definition.DefinitionSpringConfig;
import com.ouer.solar.search.rns.RealtimeUpdateConfig;
import com.ouer.solar.search.rns.dynamic.RealtimeUpdateManager;
import com.ouer.solar.search.rns.dynamic.RnsLifecycle;
import com.ouer.solar.search.rns.sync.RnsSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({SearchDalSpringConfig.class,
		   DefinitionSpringConfig.class,
		   TransportSpringConfig.class,
		   JmxSpringConfig.class,
		   ConfigSyncSpring.class})
@ImportResource("classpath:/META-INF/applicationContext-search-rns.xml")
public class RnsSpringConfig {

	@Autowired
	@Bean
	RealtimeUpdateManager realtimeUpdateManager(RealtimeUpdateConfig config,
				 									   TransportClientManager clientManager,
				 									   DefinitionFactoryManager definitionManager,
				 									   SearchDalManager dalManager) {
		return new RealtimeUpdateManager(config, clientManager, definitionManager, dalManager);
	}

	@Autowired
	@Bean
	RnsLifecycle rnsLifecycle(SearchConfigLocator locator,
			 					ConfigSyncSubscriber subscriber,
			 					RealtimeUpdateManager manager) {
		return new RnsLifecycle(locator, subscriber, manager);
	}

	@Autowired
	@Bean
	RnsSyncListener rnsSyncListener(BusSignalManager bsm, RnsLifecycle lifecycle) {
		final RnsSyncListener listener = new RnsSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}
}
