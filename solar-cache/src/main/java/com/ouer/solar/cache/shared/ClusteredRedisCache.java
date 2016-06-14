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
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.ouer.solar.cache.CacheException;
import com.ouer.solar.cache.SharedCache;
import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClusteredRedisCache implements SharedCache {

	private static final Logger LOG = LoggerFactory.getLogger(ClusteredRedisCache.class);

	private final String namespace;
	private final JedisCluster cluster;
	private final Serializer serializer;

	public ClusteredRedisCache(String namespace, JedisCluster cluster, Serializer serializer) {
		this.namespace = namespace;
		this.cluster = cluster;
		this.serializer = serializer;
	}

	@Override
	public Object get(Object key) throws CacheException {
		Object value = null;
		final byte[] bytes = cluster.get(RedisCacheUtils.encodeKey(namespace, key).getBytes());
		if (bytes != null) {
			try {
				value = serializer.deserialize(bytes);
			}
			catch (final Exception e) {
				LOG.error("error occured when get data from L2 cache", e);
				if(e instanceof IOException || e instanceof NullPointerException) {
					evict(key);
				}
			}
		}

		return value;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		if (value == null) {
			evict(key);
		} else {
			try {
				cluster.set(RedisCacheUtils.encodeKey(namespace, key).getBytes(), serializer.serialize(value));
			}
			catch (final Exception e) {
				throw new CacheException(e);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List keys() throws CacheException {
		final List<String> keys = new ArrayList<String>();
		final Collection<JedisPool> jedisPools = cluster.getClusterNodes().values();
		Jedis jedis;
		for (final JedisPool jedisPool : jedisPools) {
			jedis = jedisPool.getResource();
			try {
				keys.addAll(jedis.keys(namespace + ":*"));
			} finally {
				jedis.close();
			}
		}

		for (int i = 0; i < keys.size(); i++) {
			keys.set(i, RedisCacheUtils.decodeKey(namespace, keys.get(i)));
		}
		return keys;
	}

	@Override
	public void evict(Object key) throws CacheException {
		try {
			cluster.del(RedisCacheUtils.encodeKey(namespace, key));
		} catch (final Exception e) {
			throw new CacheException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void evict(List keys) throws CacheException {
		if (keys == null || keys.size() == 0) {
			return ;
		}

		final String[] okeys = new String[keys.size()];
		for (int i = 0; i < okeys.length; i++) {
			okeys[i] = RedisCacheUtils.encodeKey(namespace, keys.get(i));
		}

		final Collection<JedisPool> jedisPools = cluster.getClusterNodes().values();
		Jedis jedis;
		for (final JedisPool jedisPool : jedisPools) {
			jedis = jedisPool.getResource();
			try {
				jedis.del(okeys);
			} finally {
				jedis.close();
			}
		}
	}

	@Override
	public void clear() throws CacheException {
		String[] keys = new String[]{};
		final Collection<JedisPool> jedisPools = cluster.getClusterNodes().values();
		Jedis jedis;
		for (final JedisPool jedisPool : jedisPools) {
			jedis = jedisPool.getResource();
			try {
				keys = jedis.keys(namespace + ":*").toArray(keys);
				jedis.del(keys);
			} finally {
				jedis.close();
			}
		}
	}

	@Override
	public void destroy() throws CacheException {
		clear();
	}

}
