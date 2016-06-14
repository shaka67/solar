/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search.db;

import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBSearchConfigAdmin implements SearchConfigAdmin {

	private final SearchConfigMapper mapper;

	public DBSearchConfigAdmin(SearchConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(SearchConfig config) {
		if (mapper.exists(config.getAppId())) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

	@Override
	public boolean remove(String appId) {
		return mapper.delete(appId);
	}

}
