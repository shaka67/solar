/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search.db;

import java.util.List;

import com.ouer.solar.config.search.SearchConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SearchConfigMapper {

	public int insert(SearchConfig config);

	public int update(SearchConfig config);

	public SearchConfig select(String appId);

	public boolean exists(String appId);

	public boolean delete(String appId);

	public List<SearchConfig> selectAll();
}
