/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.bus.BusSignalManager;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DummyBsmListener implements BusSignalListener<ConfigSyncEvent> {

	public DummyBsmListener(BusSignalManager bsm) {
		bsm.bind(ConfigSyncEvent.class, this);
	}

	@Override
	public void signalFired(ConfigSyncEvent signal) {
		System.err.println(signal);
	}

}
