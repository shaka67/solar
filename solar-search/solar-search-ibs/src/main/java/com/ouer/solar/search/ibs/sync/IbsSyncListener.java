/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.search.ibs.dynamic.IbsLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IbsSyncListener implements BusSignalListener<ConfigSyncEvent> {

	private static final Log LOG = LogFactory.getLog(IbsSyncListener.class);

	private final IbsLifecycle lifecycle;

	public IbsSyncListener(BusSignalManager bsm, IbsLifecycle lifecycle) {
		bsm.bind(ConfigSyncEvent.class, this);
		this.lifecycle = lifecycle;
	}

	@Override
	public void signalFired(ConfigSyncEvent signal) {
		final String appId = signal.getAppId();
		try {
			lifecycle.reload(appId);
		} catch (final Exception e) {
			LOG.error("", e);
		}
	}

}
