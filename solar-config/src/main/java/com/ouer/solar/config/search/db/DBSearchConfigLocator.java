/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search.db;

import java.util.List;

import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBSearchConfigLocator implements SearchConfigLocator {

	private final SearchConfigMapper mapper;

	public DBSearchConfigLocator(SearchConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public SearchConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<SearchConfig> getConfigs() {
		return mapper.selectAll();
	}

}
