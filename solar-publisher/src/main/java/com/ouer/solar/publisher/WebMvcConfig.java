/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ouer.solar.config.sync.ConfigSyncPublisher;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ProfileSpringConfig.class })
@ImportResource({ "classpath:META-INF/spring-security-context.xml" })
//@ComponentScan(includeFilters = @Filter(Controller.class), useDefaultFilters = false)
class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Bean
	PublisherController publisherController(ConfigSyncPublisher publisher) {
		return new PublisherController(publisher);
	}
}
