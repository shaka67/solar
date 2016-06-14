/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.op;

import java.util.List;

import com.ouer.solar.able.Keyable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface InIDs<T extends Keyable<Long>>
{
	public List<T> getDataListByIds(String table, List<Long> dataIds);
}
