/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.queue;

import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.dynamic.queue.RabbitRoutingConstant.ROUTING_KEY_1;

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
import com.ouer.solar.rabbitmq.HessianMessageConverter;
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
public class RabbitRoutingReceiverTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQManager manager;

	@Test
	public void testRoutingReceiver() throws Throwable {
        final DirectExchange exchange = new DirectExchange(EXCHANGE_NAME, false, true);
        final AmqpAdmin admin = manager.getAmqpAdmin("indra");
        admin.declareExchange(exchange);
        final Queue queue = admin.declareQueue();
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_1);
        admin.declareBinding(binding);
        System.out.println("[*] waiting for messages. To exit press CTRL+C");
        final HessianMessageConverter converter = new HessianMessageConverter();
        final SimpleMessageListenerContainer container = manager.createSimpleContainer("indra");
        container.setQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                final String key = message.getMessageProperties().getReceivedRoutingKey();
                final String text = (String) converter.fromMessage(message);
                System.out.println("[x] received '" + text + "' from " + key);
            }
        });
        container.start();

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