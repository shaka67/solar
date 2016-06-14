/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.workflow;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.jobclient.Job;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JVMBusEventLauncher implements WorkflowLauncher {

	private final BusSignalManager bsm;

	public JVMBusEventLauncher(BusSignalManager bsm) {
		this.bsm = bsm;
	}

	@Override
	public void launch(String appId, String group, String name, String version,
			Object[] arguments) throws Throwable {
		final Job job = new Job(appId, name, null).withGroup(group)
										 .withVersion(version)
										 .withArgument(arguments);
		bsm.signal(job);
	}

}
