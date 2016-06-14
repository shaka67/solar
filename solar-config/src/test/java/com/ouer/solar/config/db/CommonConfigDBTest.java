/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.CommonConfigHeader;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class CommonConfigDBTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CommonConfigMapper mapper;

	@Test
	public void testInsertSms() throws Exception {
		final CommonConfig config = new CommonConfig();
//		config.setAppId("indra");
		config.setConfigType("sms");
		config.setThirdparty("mandao");
		config.setConfigOptions("sn, pwd, special_service_number, mainserviceurl, standbyserviceurl, soap_action, whitelist, ext_config");
		mapper.insert(config);
	}

	@Test
	public void testInsertImage() throws Exception {
		final CommonConfig config = new CommonConfig();
		config.setConfigType("image");
		config.setThirdparty("qiniu");
		config.setConfigOptions("access_key, secret_key, buckets");
		mapper.insert(config);
	}

	@Test
	public void testUpdate() throws Exception {
		final CommonConfig config = new CommonConfig();
		config.setConfigType("sms");
		config.setThirdparty("mandao");
		config.setConfigOptions("sn, pwd, mainserviceurl, standbyserviceurl, soap_action, whitelist, updated");
		mapper.update(config);
	}

	@Test
	public void testSelect() throws Exception {
		final CommonConfigHeader header = new CommonConfigHeader();
		header.setConfigType("sms");
		header.setThirdparty("mandao");
		final CommonConfig config = mapper.select(header);
		System.err.println(config.getConfigOptions());
	}

	@Test
	public void testExists() throws Exception {
		final CommonConfigHeader header = new CommonConfigHeader();
		header.setConfigType("sms");
		header.setThirdparty("mandao");
		final boolean exists = mapper.exists(header);
		System.err.println(exists);
	}
}
