/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.dal.DataOperation;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class UpdateCursorTemplate<T extends Keyable<Long>> extends
		CursorTemplate<T> implements UpdateDataCursor<T> {

	public UpdateCursorTemplate(String table) {
        super(table);
    }

    @Override
	public Set<T> findUpdate(String table, List<Long> updatedIds) {
		if (CollectionUtil.isEmpty(updatedIds)) {
            return null;
        }
		final Set<T> datas = Sets.newHashSet();
		int i = 0;
		final List<Long> dataIds = Lists.newArrayList();
		for (final Long updatedId : updatedIds) {
			dataIds.add(updatedId);
			if (i++ == executeBatchSize) {
				datas.addAll(getMapper().getDataListByIds(table, dataIds));
				i = 0;
				dataIds.clear();
			}
		}
		datas.addAll(getMapper().getDataListByIds(table,
				updatedIds.subList(datas.size(), updatedIds.size())));
		return datas;
	}

	@Override
    public abstract DataOperation<T> getMapper();

}
