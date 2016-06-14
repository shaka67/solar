/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;

import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.dal.QueryWrapper;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Cursor<T extends Keyable<Long>>
{
	public List<T> getDataList(String table, QueryWrapper wrapper);

	public T getData(String table, int id);

	public int count(String table);

	public Integer maxIdByMod(String table, QueryWrapper wrapper);
}
