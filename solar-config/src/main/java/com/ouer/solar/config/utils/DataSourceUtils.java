/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Driver;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.config.DataSourceConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class DataSourceUtils {

	private static final String C3P0_DS = "com.mchange.v2.c3p0.ComboPooledDataSource";
	private static final String DRUID_DS = "com.alibaba.druid.pool.DruidDataSource";
	private static final String SIMPLE_DS = "org.springframework.jdbc.datasource.SimpleDriverDataSource";

	public static DataSource createDataSource(DataSourceConfig config) throws Exception {
		final String dsClass = config.getDatasourceClass();
		if (C3P0_DS.equals(dsClass)) {
			return createComboPooledDataSource(config);
		}
		if (DRUID_DS.equals(dsClass)) {
			return createDruidDataSource(config);
		}
		if (SIMPLE_DS.equals(dsClass)) {
			return createSimpleDriverDataSource(config);
		}

		throw new Exception("unsupported datasource class: " + dsClass);
	}

	private static DataSource createComboPooledDataSource(DataSourceConfig config) throws Exception {
		final ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass(config.getDriver());
		ds.setJdbcUrl(config.getUrl());
		ds.setUser(config.getUsername());
		ds.setPassword(config.getPassword());
		final String dsProps = config.getDatasourceProps();
		final String[] props = StringUtil.split(dsProps, StringPool.Symbol.SEMICOLON);

		for (final String prop : props) {
			setProperty(ds, prop);
		}
		return ds;
	}

	private static DataSource createDruidDataSource(DataSourceConfig config) throws Exception {
		final DruidDataSource ds = new DruidDataSource();
		ds.setDriver((Driver) Class.forName(config.getDriver()).newInstance());
		ds.setUrl(config.getUrl());
		ds.setUsername(config.getUsername());
		ds.setPassword(config.getPassword());
		final String dsProps = config.getDatasourceProps();
		final String[] props = StringUtil.split(dsProps, StringPool.Symbol.SEMICOLON);

		for (final String prop : props) {
			setProperty(ds, prop);
		}
		return ds;
	}

	@SuppressWarnings("unchecked")
	private static DataSource createSimpleDriverDataSource(DataSourceConfig config) throws Exception {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass((Class<? extends Driver>) Class.forName(config.getDriver()));
		ds.setUrl(config.getUrl());
		ds.setUsername(config.getUsername());
		ds.setPassword(config.getPassword());
		// TODO
		return ds;
	}

	private static void setProperty(Object target, String prop) throws Exception {
		final String key = StringUtil.split(prop, StringPool.Symbol.EQUALS)[0];
		final String value = StringUtil.split(prop, StringPool.Symbol.EQUALS)[1];
		final PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(target, key);
		Object propertyValue = value;
		final Class<?> type = descriptor.getPropertyType();
		if (boolean.class.equals(type) || Boolean.class.equals(type)) {
			propertyValue = Boolean.valueOf(value);
		} else if (int.class.equals(type) || Integer.class.equals(type)) {
			propertyValue = Integer.valueOf(value);
		} else if (long.class.equals(type) || Long.class.equals(type)) {
			propertyValue = Long.valueOf(value);
		}
		// TODO more type?
		final Method method = descriptor.getWriteMethod();
        if (method != null) { // TODO: address it later
            method.invoke(target, propertyValue);
        }
	}
}
