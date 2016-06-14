/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.local;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.cache.AbstractCacheProvider;
import com.ouer.solar.cache.Cache;
import com.ouer.solar.cache.CacheLevel;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EhCacheProvider extends AbstractCacheProvider {

	private static final Logger LOG = LoggerFactory.getLogger(EhCacheProvider.class);
//	private static final String CONFIG_XML = "ehcache.xml";

	private final BusSignalManager bsm;
	private CacheManager manager;

	public EhCacheProvider(BusSignalManager bsm) {
		this.bsm = bsm;
		manager = new CacheManager();
	}

	@Override
	protected Cache createCache(String namespace) throws Exception {
		Cache ehcache = null;
		synchronized(caches) {
        	ehcache = caches.get(namespace);
        	if(ehcache == null) {
	            net.sf.ehcache.Cache cache = manager.getCache(namespace);
	            if (cache == null) {
	                LOG.warn("could not find configuration [" + namespace + "]; using defaults.");
	                manager.addCache(namespace);
	                cache = manager.getCache(namespace);
	                LOG.debug("started ehcache namespace: " + namespace);
	            }
	            ehcache = new NetSfEhcache(cache, bsm);
	            caches.put(namespace, ehcache);
        	}
        }
		return ehcache;
	}

	@Override
	public void stop() {
		if (manager != null) {
            manager.shutdown();
            manager = null;
        }
	}

	@Override
	public CacheLevel level() {
		return CacheLevel.LOCAL;
	}

}
