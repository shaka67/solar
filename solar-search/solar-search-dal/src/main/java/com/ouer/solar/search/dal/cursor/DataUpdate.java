/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;
import java.util.Set;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DataUpdate<T>
{
	public Set<T> findUpdate(String table, List<Long> updatedIds);

	int executeBatchSize = 100;
}
