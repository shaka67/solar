/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.SafeEncoder;

import com.ouer.solar.StringPool;
import com.ouer.solar.cache.LevelableCache;
import com.ouer.solar.cache.shared.RedisCacheUtils;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ShardedRedisSignalBroadcaster extends AbstractSignalBroadcaster implements CacheSignalBroadcaster {

	private static final String DEFAULT_CHANNEL = "redis_channel";
	private static final Logger LOG = LoggerFactory.getLogger(ShardedRedisSignalBroadcaster.class);

	private final ShardedJedisPool pool;
	private final RedisCacheChannelSubscriber subscriber;
	private final Thread daemon;

	public ShardedRedisSignalBroadcaster(LevelableCache cache, CacheSignalAssistant csa, final ShardedJedisPool pool) {
		super(cache, csa);
		this.pool = pool;
		subscriber = new RedisCacheChannelSubscriber();
		daemon = new Thread(new Runnable() {
			@Override
			public void run() {
				final ShardedJedis jedis = pool.getResource();
				final Collection<Jedis> shards = jedis.getAllShards();
				for (final Jedis shard : shards) {
					shard.subscribe(subscriber, SafeEncoder.encode(DEFAULT_CHANNEL));
				}
				jedis.close();
			}
		});
		daemon.setDaemon(true);
		daemon.start();
	}

	@Override
	public void broadcastEvictSignal(String namespace, Object key) {
		final CacheSignal signal = csa.create(CacheSignalOperator.EVICT, namespace, key);
		final ShardedJedis jedis = pool.getResource();
		try {
			jedis.getShard(RedisCacheUtils.encodeKey(namespace, key)).publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
		} catch (final Exception e) {
			LOG.error("unable to delete cache, namespace=" + namespace + ",key=" + key, e);
		} finally {
			jedis.close();
		}
	}

	@Override
	public void broadcastClearSignal(String namespace) {
		final CacheSignal signal = csa.create(CacheSignalOperator.CLEAR, namespace, StringPool.Symbol.EMPTY);
		final ShardedJedis jedis = pool.getResource();


		try {
			jedis.getShard("*").publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
//			final Collection<Jedis> shards = jedis.getAllShards();
//			for (final Jedis shard : shards) {
//				shard.publish(SafeEncoder.encode(DEFAULT_CHANNEL), csa.toBytes(signal));
//			}
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
			ShardedRedisSignalBroadcaster.this.handleValidMessage(message);
		}
	}
}
