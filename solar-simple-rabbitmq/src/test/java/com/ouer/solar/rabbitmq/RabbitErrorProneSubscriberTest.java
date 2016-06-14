/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitErrorProneConstant.EXCHANGE_NAME;
import static com.ouer.solar.rabbitmq.RabbitErrorProneConstant.QUEUE_NAME;
import static com.ouer.solar.rabbitmq.RabbitErrorProneConstant.ROUTING_KEY;

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
public class RabbitErrorProneSubscriberTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private SimpleMessageListenerContainer container;

	@Test
	public void testErrorProneSubscriber() {
        final DirectExchange exchange = new DirectExchange(EXCHANGE_NAME, false, false);
        admin.declareExchange(exchange);
        final Queue queue = new Queue(QUEUE_NAME, false, false, false);
        admin.declareQueue(queue);
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        admin.declareBinding(binding);
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