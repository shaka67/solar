/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import com.ouer.solar.able.Stopable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface CacheProvider extends Stopable
{
	public Cache provide(String namespace, boolean autoCreate) throws CacheException;

	public CacheLevel level();
}
