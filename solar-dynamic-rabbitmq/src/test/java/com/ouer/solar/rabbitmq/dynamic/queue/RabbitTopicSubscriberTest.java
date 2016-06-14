/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitTopicConstant.BINDING_KEY_1;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitTopicConstant.BINDING_KEY_2;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitTopicConstant.EXCHANGE_NAME;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class RabbitTopicSubscriberTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testTopicSubscriber() throws Throwable {
        final TopicExchange exchange = new TopicExchange(EXCHANGE_NAME, false, true);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareExchange(exchange);

        final Queue queue1 = admin.declareQueue();
        final Binding binding1 = BindingBuilder.bind(queue1).to(exchange).with(BINDING_KEY_1);
        admin.declareBinding(binding1);
        final SimpleMessageListenerContainer container1 = manager.createSimpleContainer("indra");
        container1.setQueues(queue1);
        container1.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("[consumer1] received '" + message + "'");
            }
        });
        container1.start();
        System.out.println("[queue1] waiting for messages. To exit press CTRL+C");

        final Queue queue2 = admin.declareQueue();
        final Binding binding2 = BindingBuilder.bind(queue2).to(exchange).with(BINDING_KEY_2);
        admin.declareBinding(binding2);
        final SimpleMessageListenerContainer container2 = manager.createSimpleContainer("indra");
        container2.setQueues(queue2);
        container2.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("[consumer2] received '" + message + "'");
            }
        });
        container2.start();
        System.out.println("[queue2] waiting for messages. To exit press CTRL+C");

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