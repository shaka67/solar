/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import redis.clients.util.JedisClusterCRC16;

import com.ouer.solar.cache.CacheClient;
import com.ouer.solar.cache.config.CacheSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = {CacheSpringConfig.class, ProfileSpringConfig.class})
public class CacheTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CacheClient client;

	@Test
	public void simple() {
		client.set("qf-common", "key1", "indra");
		System.out.println(client.get("qf-common", "key1").getValue());
		System.out.println(client.get("qf-common", "key1").getValue());
		client.set("qf-common", "key1", "indra2");
		System.out.println(client.get("qf-common", "key1").getValue());
		client.set("qf-common", "key2", "indra");
		client.set("qf-common", "key3", "indra");
		client.set("qf-common", "key4", "indra");
		client.set("qf-common", "key5", "indra");
		client.set("qf-common", "key6", "indra");
		client.set("qf-common", "key0", "indra");

		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key0"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key0") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key1"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key1") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key2"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key2") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key3"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key3") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key4"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key4") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key5"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key5") % 16384);
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key6"));
		System.out.println(JedisClusterCRC16.getSlot("qf-common:S:key6") % 16384);
	}
}
