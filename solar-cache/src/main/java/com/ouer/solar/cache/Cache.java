/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.List;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Cache {

	public Object get(Object key) throws CacheException;

	public void put(Object key, Object value) throws CacheException;

	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException ;

	public void evict(Object key) throws CacheException;

	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException;

	public void clear() throws CacheException;

	public void destroy() throws CacheException;
}
