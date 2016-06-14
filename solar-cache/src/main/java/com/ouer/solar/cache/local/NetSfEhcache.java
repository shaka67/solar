/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.local;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.cache.CacheException;
import com.ouer.solar.cache.LocalCache;
import com.ouer.solar.cache.signal.CacheExpiredSignal;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class NetSfEhcache extends CacheEventListenerAdapter implements LocalCache {

	private final net.sf.ehcache.Cache cache;
	private final BusSignalManager bsm;

	public NetSfEhcache(net.sf.ehcache.Cache cache, BusSignalManager bsm) {
		this.cache = cache;
		this.bsm = bsm;
	}

	@Override
	public Object get(Object key) throws CacheException {
		try {
			if (key == null) {
				return null;
			} else {
                final Element element = cache.get(key);
				if ( element != null ) {
					return element.getObjectValue();
				}
			}
			return null;
		}
		catch (final net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		try {
			final Element element = new Element(key, value);
			cache.put(element);
		}
		catch (final Exception e) {
			throw new CacheException( e );
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List keys() throws CacheException {
		return cache.getKeys();
	}

	@Override
	public void evict(Object key) throws CacheException {
		try {
			cache.remove(key);
		}
		catch (final Exception e) {
			throw new CacheException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void evict(List keys) throws CacheException {
		try {
			cache.removeAll(keys);
		}
		catch (final Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {
		try {
			cache.removeAll();
		}
		catch (final Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void destroy() throws CacheException {
		try {
			cache.getCacheManager().removeCache(cache.getName());
		}
		catch (final Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		bsm.signal(new CacheExpiredSignal(cache.getName(), element.getObjectKey()));
	}

}
