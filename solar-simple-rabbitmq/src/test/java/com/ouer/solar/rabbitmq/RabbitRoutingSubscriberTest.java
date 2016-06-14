/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.ROUTING_KEY_2;
import static com.ouer.solar.rabbitmq.RabbitRoutingConstant.ROUTING_KEY_3;

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

import com.ouer.solar.rabbitmq.spring.NNDConfiguration;
import com.ouer.solar.rabbitmq.spring.RabbitMQConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RabbitMQConfiguration.class, NNDConfiguration.class, ProfileSpringConfig.class })
public class RabbitRoutingSubscriberTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private SimpleMessageListenerContainer container;

	@Test
	public void testRoutingSubscriber() {
        final DirectExchange exchange = new DirectExchange(EXCHANGE_NAME, false, true);
        admin.declareExchange(exchange);
        final Queue queue = admin.declareQueue();
        final HessianMessageConverter converter = new HessianMessageConverter();
        container.setQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                final String key = message.getMessageProperties().getReceivedRoutingKey();
                if (ROUTING_KEY_2.equals(key)) {
                    try {
                        Thread.sleep(500);
                    } catch (final InterruptedException e) {
                        Thread.interrupted();
                    }
                }
                final String text = (String) converter.fromMessage(message);
                System.out.println("[x] received '" + text + "' from " + key);
            }
        });
        container.start();
        System.out.println("[*] waiting for messages. To exit press CTRL+C");
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_3);
        admin.declareBinding(binding);

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