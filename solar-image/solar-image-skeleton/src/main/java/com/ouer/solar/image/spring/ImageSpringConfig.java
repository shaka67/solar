/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.bus.DefaultBusRegistry;
import com.ouer.solar.config.image.ImageConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.image.ImageApi;
import com.ouer.solar.image.ImageProxy;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigDBLocatorSpring.class,
    		ConfigDalStoreSpring.class })
public class ImageSpringConfig {

	@Bean
	BusSignalManager busSignalManager() {
		return new BusSignalManager(new DefaultBusRegistry());
	}

	@Autowired
	@Bean
	ImageApi imageApi(ImageConfigLocator locator, BusSignalManager bsm) {
		return new ImageProxy(locator, bsm);
	}

}
