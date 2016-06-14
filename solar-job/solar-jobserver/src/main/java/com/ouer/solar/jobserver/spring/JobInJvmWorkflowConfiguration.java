/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.bus.DefaultBusRegistry;
import com.ouer.solar.jobserver.workflow.JVMBusEventLauncher;
import com.ouer.solar.jobserver.workflow.WorkflowLauncher;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ RetryOperationConfiguration.class })
public class JobInJvmWorkflowConfiguration {

	@Autowired
	@Bean
	WorkflowLauncher workflowLauncher(BusSignalManager bsm) {
	    return new JVMBusEventLauncher(bsm);
	}

	@Bean
	BusSignalManager BusSignalManager() {
		return new BusSignalManager(new DefaultBusRegistry());
	}

}
