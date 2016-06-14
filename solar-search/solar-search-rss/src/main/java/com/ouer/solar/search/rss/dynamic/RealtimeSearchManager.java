/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss.dynamic;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.FilterField;
import com.ouer.solar.search.api.FilterFieldAndValue;
import com.ouer.solar.search.api.FilterType;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.RssException;
import com.ouer.solar.search.api.SearchApi;
import com.ouer.solar.search.api.SearchRequestException;
import com.ouer.solar.search.api.SimpleSearchRequest;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.rss.RealtimeSearch;
import com.ouer.solar.search.rss.RealtimeSearchConfig;
import com.ouer.solar.search.rss.RealtimeSearchProxy;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeSearchManager implements SearchApi {

	private final static Logger LOG = LoggerFactory.getLogger(RealtimeSearchManager.class);

	private final RealtimeSearchConfig config;
	private final SearchConfigLocator locator;
	private final TransportClientManager clientManager;
	private final DefinitionFactoryManager definitionManager;

	private final ConcurrentMap<String, Future<RealtimeSearch>> services = Maps.newConcurrentMap();
	private final ConcurrentMap<String, Future<Map<String, Object>>> termFilters = Maps.newConcurrentMap();

	public RealtimeSearchManager(RealtimeSearchConfig config,
									SearchConfigLocator locator,
									TransportClientManager clientManager,
									DefinitionFactoryManager definitionManager) {
		this.config = config;
		this.locator = locator;
		this.clientManager = clientManager;
		this.definitionManager = definitionManager;
	}

	public RealtimeSearch getRealtimeSearch(final String appId) {
		Future<RealtimeSearch> future = services.get(appId);
		if (future == null) {
			final Callable<RealtimeSearch> callable = new Callable<RealtimeSearch>() {
				@Override
				public RealtimeSearch call() {
					try {
						final TransportClient client = clientManager.getTransportClient(appId);
						final DefinitionFactory factory = definitionManager.getDefinitionFactory(appId);
						return createRealtimeSearch(client, factory);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<RealtimeSearch> task = new FutureTask<RealtimeSearch>(
					callable);
			future = services.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RssException(e.getCause());
		}
	}

	public void removeRealtimeSearch(String appId) throws Exception {
		removeTermFilters(appId);

		final Future<RealtimeSearch> future = services.remove(appId);
		if (future != null) {
			if (future.isDone()) {
//				future.get().stop();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		clientManager.removeTransportClient(appId);
		definitionManager.removeDefinitionFactory(appId);
	}

	public Map<String, Object> getTermFilters(final String appId) {
		Future<Map<String, Object>> future = termFilters.get(appId);
		if (future == null) {
			final Callable<Map<String, Object>> callable = new Callable<Map<String, Object>>() {
				@Override
				public Map<String, Object> call() {
					try {
						final SearchConfig config = locator.getConfig(appId);
						return config.parseTermFilters();
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<Map<String, Object>> task = new FutureTask<Map<String, Object>>(
					callable);
			future = termFilters.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			termFilters.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			termFilters.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RssException(e.getCause());
		}
	}

	public void removeTermFilters(String appId) throws Exception {
		final Future<Map<String, Object>> future = termFilters.remove(appId);
		if (future != null) {
			if (future.isDone()) {
//				future.get().stop();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}
	}

	private RealtimeSearch createRealtimeSearch(TransportClient client,
													 DefinitionFactory factory) {
		return new RealtimeSearchProxy(config, client, factory);
	}

	@Override
	public RealtimeSearchResponse search(SimpleSearchRequest request) {
		final String appId = request.getAppId();
		final Map<String, Object> filters = getTermFilters(appId);
		if (request.getFilterParams() != null) {
			request.getFilterParams().putAll(filters);
		} else {
			request.setFilterParams(filters);
		}
		return getRealtimeSearch(appId).search(request);
	}

	@Override
	public RealtimeSearchResponse search(CustomizedSearchRequest request) {
		final String appId = request.getAppId();
		final Map<String, Object> filters = getTermFilters(appId);
		final Iterator<String> it = filters.keySet().iterator();
		String key;
		Object value;
		while (it.hasNext()) {
			key = it.next();
			value = filters.get(key);
			try {
				request.addFilter(new FilterFieldAndValue(new FilterField(key, FilterType.TERM), value));
			} catch (final SearchRequestException e) {
				LOG.error("", e);
				throw new RssException(e);
			}
		}
		return getRealtimeSearch(appId).search(request);
	}

}
