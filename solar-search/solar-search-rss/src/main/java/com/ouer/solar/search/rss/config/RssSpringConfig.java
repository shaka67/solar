/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.common.TransportSpringConfig;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.definition.DefinitionSpringConfig;
import com.ouer.solar.search.rss.RealtimeSearchConfig;
import com.ouer.solar.search.rss.dynamic.RealtimeSearchManager;
import com.ouer.solar.search.rss.dynamic.RssLifecycle;
import com.ouer.solar.search.rss.sync.RssSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ConfigDBLocatorSpring.class,
		   ConfigDalStoreSpring.class,
		   DefinitionSpringConfig.class,
		   TransportSpringConfig.class,
		   JmxSpringConfig.class,
		   ConfigSyncSpring.class})
@ImportResource("classpath:/META-INF/applicationContext-search-rss.xml")
public class RssSpringConfig {

	@Autowired
	@Bean
	RealtimeSearchManager realtimeSearchManager(RealtimeSearchConfig config,
													   SearchConfigLocator locator,
													   TransportClientManager clientManager,
													   DefinitionFactoryManager definitionManager) {
		return new RealtimeSearchManager(config, locator, clientManager, definitionManager);
	}

	@Autowired
	@Bean
	RssLifecycle rssLifecycle(SearchConfigLocator locator,
			 				   ConfigSyncSubscriber subscriber,
			 				   RealtimeSearchManager manager) {
		return new RssLifecycle(locator, subscriber, manager);
	}

	@Autowired
	@Bean
	RssSyncListener rssSyncListener(BusSignalManager bsm, RssLifecycle lifecycle) {
		final RssSyncListener listener = new RssSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}
}
