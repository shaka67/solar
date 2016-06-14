/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.ROUTING_KEY_1;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.ROUTING_KEY_2;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.ROUTING_KEY_3;

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
public class RabbitRoutingPublisherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testRoutingPublisher() throws Throwable {
        final Exchange exchange = new DirectExchange(EXCHANGE_NAME, false, true);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        final RabbitTemplate template = ((RabbitMQAdmin) admin).getTemplate();
        admin.declareExchange(exchange);
        for (int i =  0; i < 100; i++) {
            final String message = "hello world . " + i;
            if (i % 3 == 0) {
                template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_1, message);
            } else if (i % 3 == 1) {
                template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_2, message);
            } else if (i % 3 == 2) {
                template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_3, message);
            }
            System.out.println("[x] sent '" + message + "'");
        }
        manager.getCachingConnectionFactory("indra").destroy();
	}

}