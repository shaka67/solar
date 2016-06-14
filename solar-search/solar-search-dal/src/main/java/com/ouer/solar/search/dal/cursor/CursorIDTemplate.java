/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.search.dal.QueryWrapper;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class CursorIDTemplate implements DataCursor<Integer> {

	protected QueryWrapper wrapper = new QueryWrapper();
    protected int total;
    private Integer maxId = 0;

	@Override
	public boolean hasNext() {
		if (maxId == 0) {
			maxId = getMapper().maxIdByMod(wrapper);
		}

		return (maxId == null) ? false : maxId > wrapper.getId();
	}

	@Override
	public List<Integer> next() {
		final List<Integer> results = getMapper().getIDList(wrapper);

		if (CollectionUtil.isNotEmpty(results)) {
			wrapper.withId(results.get(results.size() - 1));
			total += results.size();
		}

		return results;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int count() {
		return total;
	}

	public void setQuery(QueryWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public abstract CursorID getMapper();
}
