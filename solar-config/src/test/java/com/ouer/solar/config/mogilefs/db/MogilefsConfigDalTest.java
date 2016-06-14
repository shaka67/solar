/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.mogilefs.MogilefsConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class MogilefsConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MogilefsConfigMapper mapper;

	@Test
	public void testInsert() {
		MogilefsConfig config = new MogilefsConfig();
		config.setAppId("indra");
		config.setTrackers("10.8.100.41:7001/,10.8.100.42:7001");
		config.setReconnectTimeout(60000);
		config.setMaxActive(100);
		config.setMaxIdle(10);
		config.setDomain("xml");
		config.setHttpPrefix("http://files.kkkdtest.com:8888/xml/");
		mapper.insert(config);

		config = new MogilefsConfig();
		config.setAppId("qianfan");
		config.setTrackers("10.8.100.41:7001/,10.8.100.42:7001");
		config.setReconnectTimeout(60000);
		config.setMaxActive(100);
		config.setMaxIdle(10);
		config.setDomain("xml");
		config.setHttpPrefix("http://files.kkkdtest.com/xml/");
		mapper.insert(config);
	}
}
