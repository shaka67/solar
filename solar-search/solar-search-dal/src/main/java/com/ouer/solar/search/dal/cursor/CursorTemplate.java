/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.dal.QueryWrapper;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class CursorTemplate<T extends Keyable<Long>> implements
		DataCursor<T> {

	protected final String table;
	protected QueryWrapper wrapper = new QueryWrapper();
    protected int total;
    private Integer maxId = 0;

    public CursorTemplate(String table) {
    	this.table = table;
    }

	@Override
	public boolean hasNext() {
		if (maxId == 0) {
			maxId = getMapper().maxIdByMod(table, wrapper);
		}
		return (maxId == null) ? false : maxId > wrapper.getId();
	}

	@Override
	public List<T> next() {
		final List<T> results = getMapper().getDataList(table, wrapper);

		if (CollectionUtil.isNotEmpty(results)) {
			wrapper.withId(
			        results.get(results.size() - 1).getId());
			total += results.size();
			return results;
		}
		// FIXME
		wrapper.withId(wrapper.getId() + wrapper.getBatchSize());
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

	public abstract Cursor<T> getMapper();
}
