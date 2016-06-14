/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.search.rss.dynamic.RssLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RssSyncListener implements BusSignalListener<ConfigSyncEvent> {

	private static final Log LOG = LogFactory.getLog(RssSyncListener.class);

	private final RssLifecycle lifecycle;

	public RssSyncListener(RssLifecycle lifecycle) {
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
