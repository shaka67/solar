/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitQueueConstant.QUEUE_NAME;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
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
public class RabbitQueueSenderTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testQueueSender() throws Throwable {
        final Queue queue = new Queue(QUEUE_NAME, false, false, true);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareQueue(queue);
//        ((RabbitMQAdmin) admin).getTemplate().convertAndSend(queue.getName(), new byte[1024 * 1024 * 50]);
        for (int i = 0; i < 100; i++) {
            final String text = "Hello World . " + i;
            System.out.println("[x] sent '" + text + "'");
            ((RabbitMQAdmin) admin).getTemplate().convertAndSend(queue.getName(), text);
        }
        manager.getCachingConnectionFactory("indra").destroy();
    }

}