/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.op;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Upsert<T>
{
	public int saveOrUpdate(T object);
}
