/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms.db;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.google.common.collect.Lists;
import com.ouer.solar.config.sms.SmsConfig;
import com.ouer.solar.config.sms.SmsServiceObject;
import com.ouer.solar.config.sms.SmsServiceObjects;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.protocol.JsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class SmsConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SmsConfigMapper mapper;

	private final ObjectConverter converter = new JsonObjectConverter();

	@Test
	public void testInsert() throws Exception {
		final SmsConfig config = new SmsConfig();
		config.setAppId("indra");
		config.setDriver("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.8.100.2/qf-sms?autoCommit=true&useUnicode=true&autoReconnect=true");
		config.setUsername("vdlm");
		config.setPassword("pwd!@#");
		config.setDatasourceClass("com.mchange.v2.c3p0.ComboPooledDataSource");
		config.setDatasourceProps("minPoolSize=2;maxPoolSize=5;acquireIncrement=1;idleConnectionTestPeriod=18000;"
				+ "maxIdleTime=25000;maxStatementsPerConnection=10;maxStatements=400;numHelperThreads=5;"
				+ "preferredTestQuery=SELECT @@SQL_MODE;testConnectionOnCheckin=true;testConnectionOnCheckout=false;");

		final SmsServiceObject sso = new SmsServiceObject();
		sso.setRate(0);
		sso.setSmsServiceClass("com.ouer.solar.sms.server.mandao.MandaoSmsService");
		final List<SmsServiceObject> list = Lists.newArrayList();
		list.add(sso);
		final SmsServiceObjects ssos = new SmsServiceObjects();
		ssos.setList(list);

		config.setSmsServiceObjects(converter.toString(ssos));
		mapper.insert(config);
	}
}
