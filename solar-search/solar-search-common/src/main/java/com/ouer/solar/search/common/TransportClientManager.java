/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.common;

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.substringBefore;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.common.collect.Maps;
import com.ouer.solar.StringPool;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TransportClientManager {

	private static final String CLUSTER_NAME = "cluster.name";
	private static final String CLIENT_TRANSPORT_SNIFF = "client.transport.sniff";
	private static final String CLIENT_TRANSPORT_IGNORE_CLUSTER_NAME = "client.transport.ignore_cluster_name";
	private static final String CLIENT_TRANSPORT_PING_TIMEOUT = "client.transport.ping_timeout";
	private static final String CLIENT_TRANSPORT_NODES_SAMPLER_INTERVAL = "client.transport.nodes_sampler_interval";

	private static final Log LOG = LogFactory.getLog(TransportClientManager.class);

	private final SearchConfigLocator locator;

	private final ConcurrentMap<String, Future<TransportClient>> clients = Maps.newConcurrentMap();

	public TransportClientManager(SearchConfigLocator locator) {
		this.locator = locator;
	}

	public TransportClient getTransportClient(final String appId) {
		Future<TransportClient> future = clients.get(appId);
		if (future == null) {
			final Callable<TransportClient> callable = new Callable<TransportClient>() {
				@Override
				public TransportClient call() {
					try {
						final SearchConfig config = locator.getConfig(appId);
						return createTransportClient(config);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<TransportClient> task = new FutureTask<TransportClient>(
					callable);
			future = clients.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			clients.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			clients.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new TransportDynamicException(e.getCause());
		}
	}

	public void removeTransportClient(String appId) throws Exception {
		final Future<TransportClient> future = clients.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().close();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}
	}

	private TransportClient createTransportClient(SearchConfig config) {
		final TransportClient client = new TransportClient(createSettings(config));
		final String clusterNodes = config.getClusterNodes();
		for (final String clusterNode : split(clusterNodes, StringPool.Symbol.COMMA)) {
			final String hostName = substringBefore(clusterNode, StringPool.Symbol.COLON);
			final String port = substringAfter(clusterNode, StringPool.Symbol.COLON);
			LOG.info("adding transport node : " + clusterNode);
			client.addTransportAddress(new InetSocketTransportAddress(hostName, Integer.valueOf(port)));
		}
		client.connectedNodes();
		return client;
	}

	private Settings createSettings(SearchConfig config) {
		return ImmutableSettings.settingsBuilder()
				.put(CLUSTER_NAME, config.getClusterName())
				.put(CLIENT_TRANSPORT_SNIFF, config.isClientTransportSniff())
				.put(CLIENT_TRANSPORT_IGNORE_CLUSTER_NAME, config.isClientTransportIgnoreClusterName())
				.put(CLIENT_TRANSPORT_PING_TIMEOUT, config.getClientTransportPingTimeout())
				.put(CLIENT_TRANSPORT_NODES_SAMPLER_INTERVAL, config.getClientTransportNodesSamplerInterval())
				.build();
	}
}
