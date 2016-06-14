/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

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
import com.ouer.solar.search.api.IncrementUpdateFieldRequest;
import com.ouer.solar.search.api.IncrementUpdateRequest;
import com.ouer.solar.search.api.UpdateType;
import com.ouer.solar.search.rns.dynamic.RealtimeUpdateManager;
import com.ouer.solar.search.rns.spring.RnsSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RnsSpringConfig.class, ProfileSpringConfig.class })
public class RealtimeUpdateTest extends AbstractJUnit4SpringContextTests {

//	@Autowired
//	RealtimeUpdateClient client;
	@Autowired
	RealtimeUpdateManager manager;

	@Test
	public void testBase() {
		final Map<UpdateType, List<Long>> map = Maps.newHashMap();
		final List<Long> ids = Lists.newArrayList();
		ids.add(1L);
		ids.add(2L);
		map.put(UpdateType.DELETE, ids);
		manager.update(new IncrementUpdateRequest("indra", 1, map));
		synchronized (this) {
            while (true) {
                try {
                	wait();
                } catch (final Throwable e) {
                }
            }
        }
	}

	@Test
	public void testUpdate() {
		final Map<Long, Map<String,Object>> records = new HashMap<Long, Map<String,Object>>();
		final Map<String,Object> data = new HashMap<String,Object>();
		data.put("discountPrice", 888);
		data.put("amount", 888);
		records.put(9L, data);
		final IncrementUpdateFieldRequest iufr = new IncrementUpdateFieldRequest("indra", 1, records);
		manager.updateField(iufr);
	}
}
