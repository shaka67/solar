/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitTopicConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.RabbitTopicConstant.ROUTING_KEY_1;
import static com.ouer.solar.rabbitmq.RabbitTopicConstant.ROUTING_KEY_2;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
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
public class RabbitTopicPublisherTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private CachingConnectionFactory factory;

	@Test
	public void testTopicPublisher() {
        final Exchange exchange = new TopicExchange(EXCHANGE_NAME, false, true);
        admin.declareExchange(exchange);
        for (int i = 0; i < 100; i++) {
            final String message = "hello world . " + i;
            if (i % 3 == 0) {
                template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_1, message);
            } else {
                template.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_2, message);
            }
            System.out.println("[x] sent '" + message + "'");
        }
        factory.destroy();
	}

}