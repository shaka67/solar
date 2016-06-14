/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.cache.signal.CacheExpiredSignal;
import com.ouer.solar.cache.signal.CacheSignalAssistant;
import com.ouer.solar.cache.signal.CacheSignalBroadcaster;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefaultCacheClient implements CacheClient {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	private final LevelableCache cache;
	protected final CacheSignalAssistant csa;
	private final CacheSignalBroadcaster csb;

	public DefaultCacheClient(LevelableCache cache,
								 CacheSignalAssistant csa,
								 CacheSignalBroadcaster csb,
								 BusSignalManager bsm) {
		this.cache = cache;
		this.csa = csa;
		this.csb = csb;
		bsm.bind(CacheExpiredSignal.class, this);
	}

	@Override
	public CacheObject get(String namespace, Object key) {
		final CacheObject obj = new CacheObject();
		obj.setNamespace(namespace);
		obj.setKey(key);
		if (namespace != null && key != null) {
			obj.setValue(cache.get(CacheLevel.LOCAL, namespace, key));
			if (obj.getValue() == null) {
				obj.setValue(cache.get(CacheLevel.SHARED, namespace, key));
				if (obj.getValue() != null) {
					obj.setLevel(CacheLevel.SHARED);
					cache.set(CacheLevel.LOCAL, namespace, key, obj.getValue());
				}
			} else {
				obj.setLevel(CacheLevel.LOCAL);
			}
		}
		return obj;
	}

	@Override
	public void set(String namespace, Object key, Object value) {
		if (namespace != null && key != null) {
			if (value == null) {
				evict(namespace, key);
			} else {
				csb.broadcastEvictSignal(namespace, key);
				cache.set(CacheLevel.LOCAL, namespace, key, value);
				cache.set(CacheLevel.SHARED, namespace, key, value);
			}
		}
	}

	@Override
	public void evict(String namespace, Object key) {
		cache.evict(CacheLevel.LOCAL, namespace, key);
		cache.evict(CacheLevel.SHARED, namespace, key);
		csb.broadcastEvictSignal(namespace, key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void batchEvict(String namespace, List keys) {
		cache.batchEvict(CacheLevel.LOCAL, namespace, keys);
		cache.batchEvict(CacheLevel.SHARED, namespace, keys);
		csb.broadcastEvictSignal(namespace, keys);
	}

	@Override
	public void clear(String namespace) throws CacheException {
		cache.clear(CacheLevel.LOCAL, namespace);
		cache.clear(CacheLevel.SHARED, namespace);
		csb.broadcastClearSignal(namespace);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List keys(String namespace) throws CacheException {
		return cache.keys(CacheLevel.LOCAL, namespace);
	}

	@Override
	public void stop() throws Exception {
		cache.stop();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void signalFired(CacheExpiredSignal signal) {
		final String namespace = signal.getNamespace();
		final Object key = signal.getKey();

		if (LOG.isDebugEnabled()) {
			LOG.debug("cache data expired, namespace={}, key={}", namespace, key);
		}

		if(key instanceof List) {
			cache.batchEvict(CacheLevel.SHARED, namespace, (List) key);
		}
		else {
			cache.evict(CacheLevel.SHARED, namespace, key);
		}

		csb.broadcastEvictSignal(namespace, key);
	}

}
