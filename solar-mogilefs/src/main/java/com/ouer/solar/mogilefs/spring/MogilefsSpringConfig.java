/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.config.mogilefs.MogilefsConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.mogilefs.dynamic.MogilefsManager;
import com.ouer.solar.mogilefs.dynamic.MogilefsRuntimeChanger;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ConfigDBLocatorSpring.class, ConfigDalStoreSpring.class})
public class MogilefsSpringConfig {

	@Autowired
	@Bean
	MogilefsManager mogilefsManager(MogilefsConfigLocator locator) {
		return new MogilefsManager(locator);
	}

	@Autowired
	@Bean
	MogilefsRuntimeChanger mogilefsRuntimeChanger(MogilefsManager manager) {
		return new MogilefsRuntimeChanger(manager);
	}

}
