/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import redis.clients.jedis.JedisPool;

import com.ouer.solar.cache.AbstractCacheProvider;
import com.ouer.solar.cache.Cache;
import com.ouer.solar.cache.CacheLevel;
import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RedisCacheProvider extends AbstractCacheProvider {

	private final JedisPool pool;
	private final Serializer serializer;

	public RedisCacheProvider(JedisPool pool, Serializer serializer) {
		this.pool = pool;
		this.serializer = serializer;
	}

	@Override
	protected Cache createCache(String namespace) throws Exception {
//		Cache cache = null;
//		synchronized(caches) {
//			cache = caches.get(namespace);
//        	if(cache == null) {
//        		cache = new RedisCache(namespace, pool, serializer);
//	            caches.put(namespace, cache);
//        	}
//        }
//		return cache;
		return new RedisCache(namespace, pool, serializer);
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
