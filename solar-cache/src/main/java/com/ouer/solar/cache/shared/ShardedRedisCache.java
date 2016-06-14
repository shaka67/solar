/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.ouer.solar.cache.CacheException;
import com.ouer.solar.cache.SharedCache;
import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ShardedRedisCache implements SharedCache {

	private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);

	private final String namespace;
	private final ShardedJedisPool pool;
	private final Serializer serializer;

	public ShardedRedisCache(String namespace, ShardedJedisPool pool, Serializer serializer) {
		this.namespace = namespace;
		this.pool = pool;
		this.serializer = serializer;
	}

	@Override
	public Object get(Object key) throws CacheException {
		Object value = null;
		final ShardedJedis cache = pool.getResource();
		try {
			if (null == key) {
				return null;
			}
			final byte[] bytes = cache.get(RedisCacheUtils.encodeKey(namespace, key).getBytes());
			if (bytes != null) {
				value = serializer.deserialize(bytes);
			}
		} catch (final Exception e) {
			LOG.error("error occured when get data from shared cache", e);
			if(e instanceof IOException || e instanceof NullPointerException) {
				evict(key);
			}
		} finally {
			cache.close();
		}
		return value;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		if (value == null) {
			evict(key);
		} else {
			final ShardedJedis cache = pool.getResource();
			try {
				cache.set(RedisCacheUtils.encodeKey(namespace, key).getBytes(), serializer.serialize(value));
			} catch (final Exception e) {
				throw new CacheException(e);
			} finally {
				cache.close();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List keys() throws CacheException {
		final ShardedJedis cache = pool.getResource();
		try {
			final List<String> keys = new ArrayList<String>();
			final Collection<Jedis> shards = cache.getAllShards();
			for (final Jedis shard : shards) {
				keys.addAll(shard.keys(namespace + ":*"));
			}
			for (int i = 0; i < keys.size(); i++) {
				keys.set(i, RedisCacheUtils.decodeKey(namespace, keys.get(i)));
			}
			return keys;
		} catch (final Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	public void evict(Object key) throws CacheException {
		final ShardedJedis cache = pool.getResource();
		try {
			cache.del(RedisCacheUtils.encodeKey(namespace, key));
		} catch (final Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void evict(List keys) throws CacheException {
		if (keys == null || keys.size() == 0) {
			return ;
		}
		final ShardedJedis cache = pool.getResource();
		try {
			final String[] okeys = new String[keys.size()];
			for (int i = 0; i < okeys.length; i++) {
				okeys[i] = RedisCacheUtils.encodeKey(namespace, keys.get(i));
			}
			final Collection<Jedis> shards = cache.getAllShards();
			for (final Jedis shard : shards) {
				shard.del(okeys);
			}
		} catch (final Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	public void clear() throws CacheException {
		final ShardedJedis cache = pool.getResource();
		try {
			String[] keys = new String[]{};
			final Collection<Jedis> shards = cache.getAllShards();
			for (final Jedis shard : shards) {
				keys = shard.keys(namespace + ":*").toArray(keys);
				shard.del(keys);
			}
		} catch (final Exception e) {
			throw new CacheException(e);
		} finally {
			cache.close();
		}
	}

	@Override
	public void destroy() throws CacheException {
		clear();
	}

}
