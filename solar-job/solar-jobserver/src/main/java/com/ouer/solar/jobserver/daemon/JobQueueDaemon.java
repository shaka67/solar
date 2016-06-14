/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.daemon;

import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.collect.Sets;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.daemon.AbstractDaemon;
import com.ouer.solar.jobclient.JobRequestTypes;
import com.ouer.solar.jobserver.queue.JobQueueSubscriber;
import com.ouer.solar.rabbitmq.SimpleMessageListenerAdapater;
import com.ouer.solar.rabbitmq.SimpleMessageSubscriber;
import com.ouer.solar.rabbitmq.dynamic.SimpleMessageProcessorContainer;

/**
 * Daemon service used to manage the life cycle of the underlying RabbitMQ message subscribers.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobQueueDaemon extends AbstractDaemon {

    private static final String ME = "[JobQueue Daemon]";
    private static final Logger LOG = LoggerFactory.getLogger(JobQueueDaemon.class);

    private final AmqpAdmin admin;
    private final Exchange exchange;
    private final Queue queue;
    private final SimpleMessageProcessorContainer container;
    private final JobQueueSubscriber subscriber;
    private final Set<SimpleMessageProcessorContainer> containers;
    private final MessageConverter converter;

    public JobQueueDaemon(AmqpAdmin admin,
    						  ConnectionFactory factory,
    						  ExecutorService executor,
    						  RabbitMQConfig config,
                          	  JobQueueSubscriber subscriber,
                          	  MessageConverter converter)
    {
       this.admin = admin;
       this.container = new SimpleMessageProcessorContainer(factory, executor, config);
       this.subscriber = subscriber;
       containers = Sets.newHashSet();
       this.converter = converter;

       exchange = new DirectExchange(JobRequestTypes.DEFAULT_JOB_EXCHANGE_NAME, true, false);
       queue = new Queue(JobRequestTypes.DEFAULT_JOB_QUEUE_NAME, true, false, false);
    }

    @Override
    public void start()
    {
        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to start...");
        }

        // declare default job exchange
        admin.declareExchange(exchange);

        // declare default job queue
        admin.declareQueue(queue);

        for (final JobRequestTypes type :  JobRequestTypes.values()) {
            // declare binding for each job queue type
            final Binding binding = BindingBuilder.bind(queue).to(exchange).with(type.routing()).noargs();
            admin.declareBinding(binding);

            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " declared job queue binding for: [" + type + "]");
            }
        }

        // create message listener adapter
        final SimpleMessageListenerAdapater adapter = new SimpleMessageListenerAdapater();
        adapter.setDefaultListenerMethod(SimpleMessageSubscriber.DEFAULT_METHOD);
        adapter.setMessageConverter(converter);
        adapter.setDelegate(subscriber);

        // create message listener container
        container.setQueues(queue);
        container.setMessageListener(adapter);
        containers.add(container);

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " registered message subsciber for job queue: [" + queue + "]");
        }

        for (final SimpleMessageProcessorContainer container : containers) {
            container.initialize();
            container.start();
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " started now.");
        }
    }

    @Override
    public void shutdown()
    {
        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to shutdown...");
        }

        for (final SimpleMessageProcessorContainer container : containers) {
            container.shutdown();
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " terminated " + containers.size() + " connection channels.");
        }

        containers.clear();
    }

}