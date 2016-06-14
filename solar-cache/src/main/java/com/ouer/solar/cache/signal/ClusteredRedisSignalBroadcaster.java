/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.SafeEncoder;

import com.ouer.solar.StringPool;
import com.ouer.solar.cache.LevelableCache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClusteredRedisSignalBroadcaster extends AbstractSignalBroadcaster implements CacheSignalBroadcaster {

	private static final String DEFAULT_CHANNEL = "redis_channel";
	private static final Logger LOG = LoggerFactory.getLogger(ClusteredRedisSignalBroadcaster.class);

	private final JedisCluster cluster;
	private final RedisCacheChannelSubscriber subscriber;
	private final Thread daemon;

	public ClusteredRedisSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, final JedisCluster cluster) {
		super(cache, csa);
		this.cluster = cluster;
		subscriber = new RedisCacheChannelSubscriber();
		daemon = new Thread(new Runnable() {
			@Override
			public void run() {
				cluster.subscribe(subscriber, SafeEncoder.encode(DEFAULT_CHANNEL));
			}
		});
		daemon.setDaemon(true);
		daemon.start();
	}

	@Override
	public void broadcastEvictSignal(String namespace, Object key) {
		final CacheSignal signal = csa.create(CacheSignalOperator.EVICT, namespace, key);
		cluster.publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
	}

	@Override
	public void broadcastClearSignal(String namespace) {
		final CacheSignal signal = csa.create(CacheSignalOperator.CLEAR, namespace, StringPool.Symbol.EMPTY);
		cluster.publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
	}

	class RedisCacheChannelSubscriber extends BinaryJedisPubSub {

		@Override
		public void onMessage(byte[] channel, byte[] message) {
			if (message != null && message.length <= 0) {
				LOG.warn("message is empty.");
				return;
			}
			ClusteredRedisSignalBroadcaster.this.handleValidMessage(message);
		}
	}

}
