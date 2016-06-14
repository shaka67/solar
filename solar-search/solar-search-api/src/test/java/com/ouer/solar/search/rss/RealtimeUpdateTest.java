/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.search.api.IncrementUpdateApi;
import com.ouer.solar.search.api.IncrementUpdateFieldRequest;
import com.ouer.solar.search.api.IncrementUpdateRequest;
import com.ouer.solar.search.api.UpdateType;
import com.ouer.solar.search.api.spring.SearchApiSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { SearchApiSpringConfig.class, ProfileSpringConfig.class })
public class RealtimeUpdateTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private IncrementUpdateApi api;

//	@SuppressWarnings("static-access")
	@Test
	public void testDelete() throws InterruptedException {
		final Map<UpdateType, List<Long>> map = Maps.newHashMap();
		final List<Long> ids = Lists.newArrayList();
		ids.add(517L);
//		ids.add(2L);
		map.put(UpdateType.DELETE, ids);
		api.update(new IncrementUpdateRequest("c2c-gamma", 1, map));
		System.out.println("just for set breakpoint here");
	}

	@Test
	public void testUpdateField() {
		final Map<Long, Map<String,Object>> records = new HashMap<Long, Map<String,Object>>();
		final Map<String,Object> data = new HashMap<String,Object>();
		data.put("discountPrice", 999);
		data.put("amount", 999);
		records.put(516L, data);
		final IncrementUpdateFieldRequest iufr = new IncrementUpdateFieldRequest("c2c-gamma", 1, records);
		api.updateField(iufr);
	}
}
