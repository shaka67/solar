/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.util.List;

import com.ouer.solar.able.Stopable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface LevelableCache extends Stopable {

	public Object get(CacheLevel level, String namespace, Object key);

	public void set(CacheLevel level, String namespace, Object key, Object value);

	public void evict(CacheLevel level, String namespace, Object key);

	@SuppressWarnings("rawtypes")
	public void batchEvict(CacheLevel level, String namespace, List keys);

	public void clear(CacheLevel level, String namespace) throws CacheException;

	@SuppressWarnings("rawtypes")
	public List keys(CacheLevel level, String namespace) throws CacheException;
}
