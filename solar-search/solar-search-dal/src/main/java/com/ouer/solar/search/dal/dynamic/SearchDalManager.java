/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.dynamic;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.ouer.solar.config.DataSourceManager;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.search.dal.mybatis.EsSqlSessionFactoryBean;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchDalManager extends DataSourceManager<SearchConfig> {

	public SearchDalManager(SearchConfigLocator locator) {
		super(locator);
	}

	@Override
	protected SqlSessionFactory createSqlSessionFactory(SearchConfig config) throws Exception {
		final EsSqlSessionFactoryBean fb = new EsSqlSessionFactoryBean();
		fb.setConfigLocation(getConfigFile(config));
		fb.setDataSource(createDataSource(config));
		return fb.getObject();
	}

	@Override
	protected Resource getConfigFile(SearchConfig config) throws Exception {
		final String dalConfigFile = config.getDalConfigFile();
		return new UrlResource(dalConfigFile);
	}
}
