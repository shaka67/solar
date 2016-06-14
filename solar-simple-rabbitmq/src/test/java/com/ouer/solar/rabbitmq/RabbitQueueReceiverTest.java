/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitQueueConstant.QUEUE_NAME;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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
public class RabbitQueueReceiverTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private SimpleMessageListenerContainer container;

	@Test
	public void testQueueReceiver() {
        final Queue queue = new Queue(QUEUE_NAME, false, false, true);
        admin.declareQueue(queue);
        container.setQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("[x] received '" + message + "'");
            }
        });
        container.start();
        System.out.println("[*] waiting for messages. To exit press CTRL+C");

        synchronized (this) {
            while (true) {
                try {
                	wait();
                } catch (final Throwable e) {
                }
            }
        }
    }

}