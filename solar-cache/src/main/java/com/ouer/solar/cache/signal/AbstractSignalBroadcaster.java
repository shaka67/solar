/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.cache.LevelableCache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractSignalBroadcaster implements CacheSignalBroadcaster {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LevelableCache cache;
	protected final CacheSignalAssistant csa;

	public AbstractSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa) {
		this.cache = cache;
		this.csa = csa;
	}

	protected void handleValidMessage(byte[] message) {
		try {
			final CacheSignal signal = csa.parse(message);
			if (signal == null || csa.isLocalSignal(signal)) {
				return;
			}

			if (this instanceof CacheSignalHandler) {
				((CacheSignalHandler) this).handle(signal);
				return;
			}
			if (cache instanceof CacheSignalHandler) {
				((CacheSignalHandler) cache).handle(signal);
				return;
			}
			LOG.warn("unable to handle cmd {}", signal);
		} catch (final Exception e) {
			LOG.error("unable to handle received msg", e);
		}
	}
}
