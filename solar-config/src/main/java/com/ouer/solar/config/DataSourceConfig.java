/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DataSourceConfig extends BaseConfig {

	protected String driver;
	protected String url;
	protected String username;
	protected String password;
	protected String datasourceClass;
	protected String datasourceProps;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatasourceClass() {
		return datasourceClass;
	}

	public void setDatasourceClass(String datasourceClass) {
		this.datasourceClass = datasourceClass;
	}

	public String getDatasourceProps() {
		return datasourceProps;
	}

	public void setDatasourceProps(String datasourceProps) {
		this.datasourceProps = datasourceProps;
	}

}
