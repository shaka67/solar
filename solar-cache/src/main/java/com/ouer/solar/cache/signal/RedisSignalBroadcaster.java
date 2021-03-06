/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import com.ouer.solar.StringPool;
import com.ouer.solar.cache.LevelableCache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RedisSignalBroadcaster extends AbstractSignalBroadcaster implements CacheSignalBroadcaster {

	private static final String DEFAULT_CHANNEL = "redis_channel";
	private static final Logger LOG = LoggerFactory.getLogger(RedisSignalBroadcaster.class);

	private final JedisPool pool;
	private final RedisCacheChannelSubscriber subscriber;
	private final Thread daemon;

	public RedisSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, final JedisPool pool) {
		super(cache, csa);
		this.pool = pool;
		subscriber = new RedisCacheChannelSubscriber();
		daemon = new Thread(new Runnable() {
			@Override
			public void run() {
				final Jedis jedis = pool.getResource();
				jedis.subscribe(subscriber, SafeEncoder.encode(DEFAULT_CHANNEL));
				jedis.close();
			}
		});
		daemon.setDaemon(true);
		daemon.start();
	}

	@Override
	public void broadcastEvictSignal(String namespace, Object key) {
		final CacheSignal signal = csa.create(CacheSignalOperator.EVICT, namespace, key);
		final Jedis jedis = pool.getResource();
		try {
			jedis.publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
		} catch (final Exception e) {
			LOG.error("unable to delete cache, namespace=" + namespace + ",key=" + key, e);
		} finally {
			jedis.close();
		}
	}

	@Override
	public void broadcastClearSignal(String namespace) {
		final CacheSignal signal = csa.create(CacheSignalOperator.CLEAR, namespace, StringPool.Symbol.EMPTY);
		final Jedis jedis = pool.getResource();
		try {
			jedis.publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
		} catch (final Exception e) {
			LOG.error("unable to clear cache, namespace=" + namespace, e);
		} finally {
			jedis.close();
		}
	}

	class RedisCacheChannelSubscriber extends BinaryJedisPubSub {

		@Override
		public void onMessage(byte[] channel, byte[] message) {
			if (message != null && message.length <= 0) {
				LOG.warn("message is empty.");
				return;
			}
			RedisSignalBroadcaster.this.handleValidMessage(message);
		}
	}
}
