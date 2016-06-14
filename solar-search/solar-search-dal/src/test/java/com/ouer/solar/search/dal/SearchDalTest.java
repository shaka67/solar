/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.search.dal.dynamic.SearchDalManager;
import com.ouer.solar.search.dal.spring.SearchDalSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { SearchDalSpringConfig.class, ProfileSpringConfig.class })
public class SearchDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SearchDalManager manager;

	@Test
	public void testDalStart() throws Exception {
		System.err.println("es dal starts successfully!");
		final IndexMapper indexMapper = manager.newMapperFactoryBean("indra", IndexMapper.class).getObject();
		final int count = indexMapper.count("product");
		System.err.println("product table is totally " + count + " count.");
	}
}
