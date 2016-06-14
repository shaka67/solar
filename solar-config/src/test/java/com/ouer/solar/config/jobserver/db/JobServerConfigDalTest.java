/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.jobserver.JobServerSpecialConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class JobServerConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private JobServerSpecialConfigMapper mapper;

	@Test
	public void testInsert() {
		JobServerSpecialConfig config = new JobServerSpecialConfig();
		config.setAppId("indra");
		config.setInstanceName("IndraJobScheduler");
		config.setDbUrl("jdbc:mysql://localhost:3306/job-indra");
		config.setDbUser("root");
		config.setDbPassword("123456");
		mapper.insert(config);

		config = new JobServerSpecialConfig();
		config.setAppId("qianfan");
		config.setInstanceName("QfJobScheduler");
		config.setDbUrl("jdbc:mysql://localhost:3306/job-qianfan");
		config.setDbUser("root");
		config.setDbPassword("123456");
		mapper.insert(config);
	}

	@Test
	public void testSelect() {
		final JobServerSpecialConfig config = mapper.select("indra");
		System.err.println(config);
	}

	@Test
	public void testDelete() {
		mapper.delete("indra");
	}

}
