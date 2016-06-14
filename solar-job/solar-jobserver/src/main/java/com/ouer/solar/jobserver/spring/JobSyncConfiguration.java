/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.jobserver.dynamic.JobServerLifecycle;
import com.ouer.solar.jobserver.sync.JobserverSyncListener;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class })
public class JobSyncConfiguration {

	@Autowired
	@Bean
	JobserverSyncListener jobserverSyncListener(BusSignalManager bsm, JobServerLifecycle lifecycle) {
		return new JobserverSyncListener(bsm, lifecycle);
	}
}
