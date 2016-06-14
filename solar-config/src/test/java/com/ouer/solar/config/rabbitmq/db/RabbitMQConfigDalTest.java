/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class RabbitMQConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQConfigMapper mapper;

	@Test
	public void testInsert() {
		RabbitMQConfig config = new RabbitMQConfig();
		config.setAppId("indra");
		config.setHost("localhost");
		config.setPort(5672);
		config.setUsername("guest");
		config.setPassword("guest");
		config.setVirtualHost("local-test");
		mapper.insert(config);

		config = new RabbitMQConfig();
		config.setAppId("qianfan");
		config.setHost("localhost");
		config.setPort(5672);
		config.setUsername("guest");
		config.setPassword("guest");
		config.setVirtualHost("qf-test-alpha");
		mapper.insert(config);
	}

	@Test
	public void testSelect() {
		final RabbitMQConfig config = mapper.select("indra");
		System.err.println(config);
	}

	@Test
	public void testDelete() {
		mapper.delete("indra");
	}

}
