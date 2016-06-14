/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search.db;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.config.utils.DataSourceUtils;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class SearchConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SearchConfigMapper mapper;

	@Test
	public void testInsert() {
		SearchConfig config = new SearchConfig();
		config.setAppId("indra");
		config.setClusterNodes("10.8.100.127:9300");
		config.setClusterName("qianfan");
		config.setClientTransportSniff(true);
		config.setClientTransportIgnoreClusterName(false);
		config.setClientTransportPingTimeout("5s");
		config.setClientTransportNodesSamplerInterval("5s");

		config.setDalConfigFile("http://file.kkkdtest.com:8888/xml/MapperConfig-indra.xml");
		config.setDriver("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.8.100.2:3306/qf_product?characterEncoding=utf-8&autoReconnect=true");
		config.setUsername("qf_product");
		config.setPassword("ouer@2015byYl");
		config.setDatasourceClass("com.mchange.v2.c3p0.ComboPooledDataSource");
		config.setDatasourceProps("minPoolSize=2;maxPoolSize=5;acquireIncrement=1;idleConnectionTestPeriod=18000;"
				+ "maxIdleTime=25000;maxStatementsPerConnection=10;maxStatements=400;numHelperThreads=5;"
				+ "preferredTestQuery=SELECT @@SQL_MODE;testConnectionOnCheckin=true;testConnectionOnCheckout=false;");

		config.setIndexDefinitionFile("http://file.kkkdtest.com:8888/xml/es-index.xml");
		config.setResultDefinitionFile("http://file.kkkdtest.com:8888/xml/es-result-36.xml");
		config.setSearchDefinitionFile("http://file.kkkdtest.com:8888/xml/es-search-36.xml");

		config.setJobCronExpression("0 0 4 * * ?");
		config.setTermFilters("domain=36");
		mapper.insert(config);

		config = new SearchConfig();
		config.setAppId("davebella-test");
		config.setClusterNodes("10.8.110.24:9300");
		config.setClusterName("qianfan-dwbl");
		config.setClientTransportSniff(true);
		config.setClientTransportIgnoreClusterName(false);
		config.setClientTransportPingTimeout("5s");
		config.setClientTransportNodesSamplerInterval("5s");

		config.setDalConfigFile("http://file.kkkdtest.com:8888/xml/MapperConfig-indra.xml");
		config.setDriver("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.8.100.2:3306/qf_dwbl_product?characterEncoding=utf-8&autoReconnect=true");
		config.setUsername("qf_dwbl");
		config.setPassword("ouer@2016");
		config.setDatasourceClass("com.mchange.v2.c3p0.ComboPooledDataSource");
		config.setDatasourceProps("minPoolSize=2;maxPoolSize=5;acquireIncrement=1;idleConnectionTestPeriod=18000;"
				+ "maxIdleTime=25000;maxStatementsPerConnection=10;maxStatements=400;numHelperThreads=5;"
				+ "preferredTestQuery=SELECT @@SQL_MODE;testConnectionOnCheckin=true;testConnectionOnCheckout=false;");

		config.setIndexDefinitionFile("http://file.kkkdtest.com:8888/xml/es-index.xml");
		config.setResultDefinitionFile("http://file.kkkdtest.com:8888/xml/es-result-36.xml");
		config.setSearchDefinitionFile("http://file.kkkdtest.com:8888/xml/es-search-36.xml");

		config.setJobCronExpression("0 0 4 * * ?");
		config.setTermFilters("domain=49");
		mapper.insert(config);
	}

	@Test
	public void testLoadDataSource() throws Exception {
		SearchConfig config = mapper.select("indra");
		DataSource ds = DataSourceUtils.createDataSource(config);
		System.err.println(ds);
		config = mapper.select("davebella-test");
		ds = DataSourceUtils.createDataSource(config);
		System.err.println(ds);
	}
}
