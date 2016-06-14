/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import static com.ouer.solar.rabbitmq.RabbitTaskConstant.QUEUE_NAME;

import org.junit.Test;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.rabbitmq.spring.NNDConfiguration;
import com.ouer.solar.rabbitmq.spring.RabbitMQConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;
import com.rabbitmq.client.Channel;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RabbitMQConfiguration.class, NNDConfiguration.class, ProfileSpringConfig.class })
public class RabbitTaskReceiverTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AmqpAdmin admin;
	@Autowired
	private SimpleMessageListenerContainer container;

	@Test
	public void testTaskReceiver() {
        final Queue queue = new Queue(QUEUE_NAME, false, false, true);
        admin.declareQueue(queue);
        container.setQueues(queue);
        // don't dispatch new message to a receiver until it has processed and
        // acknowledged the previous one.
        container.setPrefetchCount(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println("[x] received '" + message + "'");
                doWork(new String(message.getBody()));
                System.out.println("[x] done");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        container.start();
        System.out.println("[*] waiting for message. To exit press CTRL+C");

        synchronized (this) {
            while (true) {
                try {
                	wait();
                } catch (final Throwable e) {
                }
            }
        }
	}

    private void doWork(String message) throws InterruptedException {
        for (final char character: message.toCharArray()) {
            if (character == '.') {
                Thread.sleep(1000);
            }
        }
    }

}