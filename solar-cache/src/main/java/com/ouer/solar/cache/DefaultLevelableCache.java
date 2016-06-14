/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.cache.signal.CacheSignal;
import com.ouer.solar.cache.signal.CacheSignalHandler;
import com.ouer.solar.cache.signal.CacheSignalOperator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefaultLevelableCache implements LevelableCache, CacheSignalHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLevelableCache.class);

	private final CacheProvider localProvider;
	private final CacheProvider sharedProvider;
	private final SignalHandler signalHandler;

	public DefaultLevelableCache(CacheProvider localProvider, CacheProvider sharedProvider) {
		if (CacheLevel.LOCAL.equals(localProvider.level())) {
			this.localProvider = localProvider;
		} else {
			throw new CacheException("cache provider: " + localProvider.getClass().getName()
					+ " is not a local level provider!");
		}
		if (CacheLevel.SHARED.equals(sharedProvider.level())) {
			this.sharedProvider = sharedProvider;
		} else {
			throw new CacheException("cache provider: " + sharedProvider.getClass().getName()
					+ " is not a shared level provider!");
		}
		signalHandler = new SignalHandler();
	}

	@Override
	public Object get(CacheLevel level, String namespace, Object key) {
		if (namespace != null && key != null) {
            final Cache cache = selectCache(level, namespace, false);
            if (cache != null) {
				return cache.get(key);
			}
        }
		return null;
	}

	@Override
	public void set(CacheLevel level, String namespace, Object key, Object value) {
		if (namespace != null && key != null && value != null) {
            final Cache cache = selectCache(level, namespace, true);
            if (cache != null) {
				cache.put(key, value);
			}
        }
	}

	@Override
	public void evict(CacheLevel level, String namespace, Object key) {
		if (namespace != null && key != null) {
            final Cache cache = selectCache(level, namespace, false);
            if (cache != null) {
				cache.evict(key);
			}
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void batchEvict(CacheLevel level, String namespace, List keys) {
		if (namespace != null && keys != null && keys.size() > 0) {
            final Cache cache = selectCache(level, namespace, false);
            if (cache != null) {
				cache.evict(keys);
			}
        }
	}

	@Override
	public void clear(CacheLevel level, String namespace) throws CacheException {
		final Cache cache = selectCache(level, namespace, false);
        if (cache != null) {
			cache.clear();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List keys(CacheLevel level, String namespace) throws CacheException {
		final Cache cache = selectCache(level, namespace, false);
		return (cache != null) ? cache.keys() : null;
	}

	@Override
	public void stop() throws Exception {
		localProvider.stop();
		sharedProvider.stop();
	}

	private Cache selectCache(CacheLevel level, String namespace, boolean autoCreate) {
		return selectCacheProvider(level).provide(namespace, autoCreate);
	}

	private CacheProvider selectCacheProvider(CacheLevel level) throws CacheException {
		if (CacheLevel.LOCAL.equals(level)) {
			return localProvider;
		}
		if (CacheLevel.SHARED.equals(level)) {
			return sharedProvider;
		}
		throw new CacheException("no such cache level: " + level + "!");
	}

	@Override
	public void handle(CacheSignal signal) {
		signalHandler.handle(signal);
	}

	class SignalHandler {

		public void handle(CacheSignal signal) {
			final CacheSignalOperator operator = signal.getOperator();
			if (CacheSignalOperator.EVICT.equals(operator)) {
				deleteCacheKey(signal.getNamespace(), signal.getKey());
				return;
			}
			if (CacheSignalOperator.CLEAR.equals(operator)) {
				clearCacheKey(signal.getNamespace());
				return;
			}
		}

		@SuppressWarnings("rawtypes")
		protected void deleteCacheKey(String namespace, Object key) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("received cache evict message, namespace={}, key={}", namespace, key);
			}
			if (key instanceof List) {
				DefaultLevelableCache.this.batchEvict(CacheLevel.LOCAL, namespace, (List) key);
			} else {
				DefaultLevelableCache.this.evict(CacheLevel.LOCAL, namespace, key);
			}
		}

		protected void clearCacheKey(String namespace) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("received cache clear message, namespace={}", namespace);
			}
			DefaultLevelableCache.this.clear(CacheLevel.LOCAL, namespace);
		}
	}

}
