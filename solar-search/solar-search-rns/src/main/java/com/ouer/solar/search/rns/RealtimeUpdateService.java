/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

import java.util.Set;

import com.ouer.solar.search.common.Indexable;
import com.ouer.solar.search.common.KeyableMap;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RealtimeUpdateService extends Indexable
{
	public void updateIndex(Set<Long> deletedIds, Set<KeyableMap<String, Object, Long>> updateDatas);
}
