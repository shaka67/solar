/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitErrorProneConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitErrorProneConstant.ROUTING_KEY;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.rabbitmq.RabbitMQAdmin;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQManager;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { DynamicRabbitMQConfiguration.class,
									 ConfigDBLocatorSpring.class,
									 ConfigDalStoreSpring.class,
		 							 ProfileSpringConfig.class })
public class RabbitErrorPronePublisherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testErrorPronePublisher() throws Throwable {
		final Exchange exchange = new DirectExchange(EXCHANGE_NAME, false, false);
		final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareExchange(exchange);
        final String message = "hello world . " + 0;
        final RabbitTemplate template = ((RabbitMQAdmin) admin).getTemplate();
        template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        System.out.println("[x] sent '" + message + "'");
        manager.getCachingConnectionFactory("indra").destroy();
	}

}