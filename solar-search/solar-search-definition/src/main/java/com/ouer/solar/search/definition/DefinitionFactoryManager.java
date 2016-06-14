/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefinitionFactoryManager {

	private static final Logger LOG = LoggerFactory.getLogger(DefinitionFactoryManager.class);

	private final SearchConfigLocator locator;

	private final ConcurrentMap<String, Future<DefinitionFactory>> factories = Maps.newConcurrentMap();

	public DefinitionFactoryManager(SearchConfigLocator locator) {
		this.locator = locator;
	}

	public DefinitionFactory getDefinitionFactory(final String appId) {
		Future<DefinitionFactory> future = factories.get(appId);
		if (future == null) {
			final Callable<DefinitionFactory> callable = new Callable<DefinitionFactory>() {
				@Override
				public DefinitionFactory call() {
					try {
						final SearchConfig config = locator.getConfig(appId);
						return createDefinitionFactory(config);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<DefinitionFactory> task = new FutureTask<DefinitionFactory>(
					callable);
			future = factories.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			factories.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			factories.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new DefinitionException(e.getCause());
		}
	}

	public void removeDefinitionFactory(String appId) throws Exception {
		final Future<DefinitionFactory> future = factories.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().clear();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}
	}

	private DefinitionFactory createDefinitionFactory(SearchConfig config) throws Exception {
		final String index = config.getIndexDefinitionFile();
		final String result = config.getResultDefinitionFile();
		final String search = config.getSearchDefinitionFile();

		return DefinitionFactoryBuilder.buildFactory(new URL(index), new URL(result), new URL(search));
	}

}
