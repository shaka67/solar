/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.location.server;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ LocationSpringDalConfig.class })
@ImportResource({ "classpath:/META-INF/applicationContext-location-server.xml" })
public class LocationSpringConfig {

	@Bean
	Mapper dozerMapper() {
		return new DozerBeanMapper();
	}

	@Autowired
	@Bean
	LocationService locationService(ZoneMapper zoneMapper, Mapper dozerMapper) {
		return new LocationService(zoneMapper, dozerMapper);
	}
}
