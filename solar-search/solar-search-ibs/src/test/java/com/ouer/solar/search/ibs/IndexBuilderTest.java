/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.search.ibs.config.IbsSpringConfig;
import com.ouer.solar.search.ibs.dynamic.IbsLifecycle;
import com.ouer.solar.search.ibs.dynamic.IndexBuilderManager;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("prod")
@ContextConfiguration(classes = { IbsSpringConfig.class, ProfileSpringConfig.class })
public class IndexBuilderTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	IndexBuilderManager manager;

	@Autowired
	IbsLifecycle lifecycle;

	@Test
	public void testBase() throws Exception {
		manager.getIndexBuildService("c2c-preprod").buildIndex();
	}

	@Test
	public void testBuild() throws Exception {
		lifecycle.buildIndex("c2c", 2);
	}

	@Test
	public void testDeleteIndex() {
		lifecycle.deleteIndex("c2c-preprod", "c2c");
	}

	@Test
	public void testBuildMapping() throws Exception {
		lifecycle.buildMapping("c2c-preprod", 2);
	}
}
