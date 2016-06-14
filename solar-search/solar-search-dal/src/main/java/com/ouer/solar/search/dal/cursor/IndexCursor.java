/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import com.ouer.solar.search.common.KeyableMap;
import com.ouer.solar.search.dal.DataOperation;
import com.ouer.solar.search.dal.IndexMapper;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexCursor extends UpdateCursorTemplate<KeyableMap<String, Object, Long>> {

    private final IndexMapper mapper;

    public IndexCursor(String table, IndexMapper mapper) {
        super(table);
        this.mapper = mapper;
    }

    @Override
    public DataOperation<KeyableMap<String, Object, Long>> getMapper() {
        return mapper;
    }

}
