/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import redis.clients.jedis.ShardedJedisPool;

import com.ouer.solar.cache.AbstractCacheProvider;
import com.ouer.solar.cache.Cache;
import com.ouer.solar.cache.CacheLevel;
import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ShardedRedisCacheProvider extends AbstractCacheProvider {

	private final ShardedJedisPool pool;
	private final Serializer serializer;

	public ShardedRedisCacheProvider(ShardedJedisPool pool, Serializer serializer) {
		this.pool = pool;
		this.serializer = serializer;
	}

	@Override
	protected Cache createCache(String namespace) throws Exception {
		return new ShardedRedisCache(namespace, pool, serializer);
	}

	@Override
	public void stop() throws Exception {
		pool.destroy();
	}

	@Override
	public CacheLevel level() {
		return CacheLevel.SHARED;
	}

}
