/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.ROUTING_KEY_1;
import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.ROUTING_KEY_2;
import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.ROUTING_KEY_3;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.rabbitmq.spring.NNDConfiguration;
import com.ouer.solar.rabbitmq.spring.RabbitMQConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RabbitMQConfiguration.class, NNDConfiguration.class, ProfileSpringConfig.class })
public class RabbitRoutingPublisherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private CachingConnectionFactory factory;

	@Test
	public void testRoutingPublisher() {
        final Exchange exchange = new DirectExchange(EXCHANGE_NAME, false, true);
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
        factory.destroy();
	}

}