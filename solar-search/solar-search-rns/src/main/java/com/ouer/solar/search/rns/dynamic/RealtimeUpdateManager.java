/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns.dynamic;

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
import com.ouer.solar.pool.BatchBuffer;
import com.ouer.solar.search.api.IncrementUpdateApi;
import com.ouer.solar.search.api.IncrementUpdateFieldRequest;
import com.ouer.solar.search.api.IncrementUpdateRequest;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.dal.IndexMapper;
import com.ouer.solar.search.dal.dynamic.SearchDalManager;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.rns.RealtimeUpdateClient;
import com.ouer.solar.search.rns.RealtimeUpdateConfig;
import com.ouer.solar.search.rns.UpdateMap;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeUpdateManager implements IncrementUpdateApi {

	private final static Logger LOG = LoggerFactory.getLogger(RealtimeUpdateManager.class);

	private final RealtimeUpdateConfig config;
	private final TransportClientManager clientManager;
	private final DefinitionFactoryManager definitionManager;
	private final SearchDalManager dalManager;

	private final ConcurrentMap<String, Future<RealtimeUpdateClient>> services = Maps.newConcurrentMap();

	public RealtimeUpdateManager(RealtimeUpdateConfig config,
			  						 TransportClientManager clientManager,
			  						 DefinitionFactoryManager definitionManager,
			  						 SearchDalManager dalManager) {
		this.config = config;
		this.clientManager = clientManager;
		this.definitionManager = definitionManager;
		this.dalManager = dalManager;
	}

	public RealtimeUpdateClient getRealtimeUpdateClient(final String appId) {
		Future<RealtimeUpdateClient> future = services.get(appId);
		if (future == null) {
			final Callable<RealtimeUpdateClient> callable = new Callable<RealtimeUpdateClient>() {
				@Override
				public RealtimeUpdateClient call() {
					try {
						final TransportClient client = clientManager.getTransportClient(appId);
						final DefinitionFactory factory = definitionManager.getDefinitionFactory(appId);
						final IndexMapper mapper = dalManager.newMapperFactoryBean(appId, IndexMapper.class).getObject();
						return createRealtimeUpdateClient(client, factory, mapper);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<RealtimeUpdateClient> task = new FutureTask<RealtimeUpdateClient>(
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
			throw new RnsDynamicException(e.getCause());
		}
	}

	public void removeRealtimeUpdateClient(String appId) throws Exception {
		final Future<RealtimeUpdateClient> future = services.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().stop();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		clientManager.removeTransportClient(appId);
		definitionManager.removeDefinitionFactory(appId);
		dalManager.removeSqlSessionFactory(appId);
	}

	private RealtimeUpdateClient createRealtimeUpdateClient(TransportClient client,
			  													 DefinitionFactory factory,
			  													 IndexMapper mapper) {
		return new RealtimeUpdateClient(config, client, factory, mapper, new BatchBuffer<UpdateMap>());
	}

	@Override
	public void update(IncrementUpdateRequest request) {
		final String appId = request.getAppId();
		getRealtimeUpdateClient(appId).update(request);
	}

	@Override
	public void updateField(IncrementUpdateFieldRequest request) {
		final String appId = request.getAppId();
		getRealtimeUpdateClient(appId).updateField(request);
	}
}
