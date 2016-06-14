/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitErrorProneConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitErrorProneConstant.QUEUE_NAME;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitErrorProneConstant.ROUTING_KEY;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
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
public class RabbitErrorProneSubscriberTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testErrorProneSubscriber() throws Throwable {
        final DirectExchange exchange = new DirectExchange(EXCHANGE_NAME, false, false);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareExchange(exchange);
        final Queue queue = new Queue(QUEUE_NAME, false, false, false);
        admin.declareQueue(queue);
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        admin.declareBinding(binding);
        final SimpleMessageListenerContainer container = manager.createSimpleContainer("indra");
        container.setQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                throw new RuntimeException("[x] throw exception on '" + message + "'");
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