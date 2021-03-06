/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.config.search.SearchConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class TransportSpringConfig {

	@Autowired
	@Bean
	TransportClientManager transportClientManager(SearchConfigLocator locator) {
		return new TransportClientManager(locator);
	}
}
