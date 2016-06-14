/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractCacheProvider implements CacheProvider {

	protected final ConcurrentMap<String, Cache> caches = Maps.newConcurrentMap();

	@Override
	public Cache provide(String namespace, boolean autoCreate)
			throws CacheException {
		if (CacheLevel.SHARED.equals(this.level())) {
			try {
				return createCache(namespace);
			} catch (final Exception e) {
				throw new CacheException(e);
			}
		}
		// local cache
		Cache cache = caches.get(namespace);
    	if (cache == null && autoCreate) {
    		try {
    			cache = createCache(namespace);
    		} catch(final Exception e) {
    			throw new CacheException(e);
    		}
    	}
        return cache;
	}

	protected abstract Cache createCache(String namespace) throws Exception;
}
