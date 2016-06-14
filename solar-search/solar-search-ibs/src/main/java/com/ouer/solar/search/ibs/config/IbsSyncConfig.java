/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.search.ibs.dynamic.IbsLifecycle;
import com.ouer.solar.search.ibs.sync.IbsSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class IbsSyncConfig {

	@Autowired
	@Bean
	IbsSyncListener ibsSyncListener(BusSignalManager bsm, IbsLifecycle lifecycle) {
		return new IbsSyncListener(bsm, lifecycle);
	}
}
