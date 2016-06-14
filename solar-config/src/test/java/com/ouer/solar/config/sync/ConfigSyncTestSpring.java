/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.spring.ConfigSyncSpring;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class ConfigSyncTestSpring {

	@Autowired
	@Bean
	DummyBsmListener DummyBsmListener(BusSignalManager bsm) {
		return new DummyBsmListener(bsm);
	}
}
