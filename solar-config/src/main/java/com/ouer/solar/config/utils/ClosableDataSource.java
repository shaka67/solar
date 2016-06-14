/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.utils;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClosableDataSource {

	private final DataSource datasource;

	public ClosableDataSource(DataSource datasource) {
		this.datasource = datasource;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void close() {
		if (datasource instanceof ComboPooledDataSource) {
			((ComboPooledDataSource) datasource).close();
			return;
		}
		if (datasource instanceof DruidDataSource) {
			((DruidDataSource) datasource).close();
			return;
		}
		// SimpleDriverDataSource does not have close method
	}
}
