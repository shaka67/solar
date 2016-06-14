/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitBroadcastConstant.EXCHANGE_NAME;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
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
public class RabbitBroadcastSubscriberTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testBroadcastSubscriber() throws Throwable {
        final Exchange exchange = new FanoutExchange(EXCHANGE_NAME, false, true);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareExchange(exchange);
        final Queue queue = admin.declareQueue();
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with("").noargs();
        admin.declareBinding(binding);
        final SimpleMessageListenerContainer container = manager.createSimpleContainer("indra");
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
