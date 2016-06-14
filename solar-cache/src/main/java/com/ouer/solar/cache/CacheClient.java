/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.List;

import com.ouer.solar.able.Stopable;
import com.ouer.solar.bus.BusSignalListener;
import com.ouer.solar.cache.signal.CacheExpiredSignal;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface CacheClient extends BusSignalListener<CacheExpiredSignal>, Stopable {

	public CacheObject get(String namespace, Object key);

	public void set(String namespace, Object key, Object value);

	public void evict(String namespace, Object key) ;

	@SuppressWarnings({ "rawtypes" })
	public void batchEvict(String namespace, List keys) ;

	public void clear(String namespace) throws CacheException;

	@SuppressWarnings("rawtypes")
	public List keys(String namespace) throws CacheException;

}
