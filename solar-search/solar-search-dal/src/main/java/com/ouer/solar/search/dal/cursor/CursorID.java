/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;

import com.ouer.solar.search.dal.QueryWrapper;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface CursorID
{
	public List<Integer> getIDList(QueryWrapper wrapper);

	public int count();

	public int maxIdByMod(QueryWrapper wrapper);
}
