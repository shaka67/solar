/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import java.util.Collection;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.ouer.solar.cache.AbstractCacheProvider;
import com.ouer.solar.cache.Cache;
import com.ouer.solar.cache.CacheLevel;
import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClusteredRedisCacheProvider extends AbstractCacheProvider {

	private final JedisCluster cluster;
	private final Serializer serializer;

	public ClusteredRedisCacheProvider(JedisCluster cluster, Serializer serializer) {
		this.cluster = cluster;
		this.serializer = serializer;
	}

	@Override
	public CacheLevel level() {
		return CacheLevel.SHARED;
	}

	@Override
	public void stop() throws Exception {
		final Collection<JedisPool> jedisPools = cluster.getClusterNodes().values();
		for (final JedisPool jedisPool : jedisPools) {
			jedisPool.destroy();
		}
	}

	@Override
	protected Cache createCache(String namespace) throws Exception {
		return new ClusteredRedisCache(namespace, cluster, serializer);
	}

}
