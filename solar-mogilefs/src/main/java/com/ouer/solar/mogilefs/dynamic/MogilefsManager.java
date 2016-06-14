/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.dynamic;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.config.mogilefs.MogilefsConfig;
import com.ouer.solar.config.mogilefs.MogilefsConfigLocator;
import com.ouer.solar.mogilefs.Mogilefs;
import com.ouer.solar.mogilefs.PooledMogilefs;
import com.ouer.solar.mogilefs.Trackers;
import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogilefsManager {

	private static final Log LOG = LogFactory.getLog(MogilefsManager.class);

	private final MogilefsConfigLocator locator;

	private final ConcurrentMap<String, Future<Mogilefs>> mogilefses = Maps.newConcurrentMap();

	public MogilefsManager(MogilefsConfigLocator locator) {
		this.locator = locator;
	}

	public Mogilefs getMogileFS(final String appId) {
		Future<Mogilefs> future = mogilefses.get(appId);
		if (future == null) {
			final Callable<Mogilefs> callable = new Callable<Mogilefs>() {
				@Override
				public Mogilefs call() {
					try {
						final MogilefsConfig config = locator.getConfig(appId);
						return createMogileFS(config);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<Mogilefs> task = new FutureTask<Mogilefs>(
					callable);
			future = mogilefses.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			mogilefses.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			mogilefses.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new MogilefsDynamicException(e.getCause());
		}
	}

	public void removeMogileFS(String appId) throws Exception {
		final Future<Mogilefs> future = mogilefses.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().stop();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}
	}

	private Mogilefs createMogileFS(MogilefsConfig config) {
		final Trackers trackers = new Trackers();
        trackers.setHosts(config.getTrackers());
        trackers.setMaxTrackerConnections(config.getMaxActive());
        trackers.setMaxIdleConnections(config.getMaxIdle());
		try {
			return new PooledMogilefs(trackers, config.getDomain(), config.getHttpPrefix());
		} catch (final NoTrackersException e) {
			throw new MogilefsDynamicException(e);
		} catch (final BadHostFormatException e) {
			throw new MogilefsDynamicException(e);
		}
	}

}
