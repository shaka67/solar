/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver;

import com.ouer.solar.config.BaseConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobServerSpecialConfig extends BaseConfig {

	public static final String INSTANCE_NAME = "org.quartz.scheduler.instanceName";
	public static final String DATASOURCE_NAME = "org.quartz.jobStore.dataSource";
	public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
//	public static final String DB_URL = "org.quartz.dataSource.solar.URL";
//	public static final String DB_USER = "org.quartz.dataSource.solar.user";
//	public static final String DB_PASSWORD = "org.quartz.dataSource.solar.password";

	private String instanceName;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDriverKey() {
		return "org.quartz.dataSource." + appId + ".driver";
	}

	public String getDbUrlKey() {
		return "org.quartz.dataSource." + appId + ".URL";
	}

	public String getDbUserKey() {
		return "org.quartz.dataSource." + appId + ".user";
	}

	public String getDbPasswordKey() {
		return "org.quartz.dataSource." + appId + ".password";
	}

	@Override
	public String toString() {
		return "JobServerSpecialConfig [appId=" + appId + ", instanceName="
				+ instanceName + ", dbUrl=" + dbUrl + ", dbUser=" + dbUser
				+ ", dbPassword=" + dbPassword + "]";
	}

}
