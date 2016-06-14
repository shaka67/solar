/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import com.google.common.collect.Sets;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.daemon.AbstractDaemon;
import com.ouer.solar.rabbitmq.HessianMessageConverter;
import com.ouer.solar.rabbitmq.RabbitMQFailureListener;
import com.ouer.solar.rabbitmq.SimpleMessageListenerAdapater;
import com.ouer.solar.rabbitmq.SimpleMessageSubscriber;
import com.ouer.solar.rabbitmq.dynamic.SimpleMessageProcessorContainer;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 * Daemon service used to manage the life cycle of the underlying RabbitMQ
 * message subscribers for configured remote services.
 * Unlink {@link RabbitMQRemoteServiceDaemon}, remote worker depends on
 * durable queue to execute long-running tasks. Thus {@link RabbitMQRemoteWorkerDaemon}
 * would declare during exchange and job as its underlying message repository.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerDaemon extends AbstractDaemon {

    private final static String ME = "[RemoteWorker Daemon]";
    private final static Log LOG = LogFactory.getLog(RabbitMQRemoteWorkerDaemon.class);

    private final AmqpAdmin admin;
    private final RabbitMQRemoteWorkerRegister register;
    private final Set<SimpleMessageProcessorContainer> containers;
    private final ConnectionFactory factory;
    private final RabbitMQConfig config;

    private final ExecutorService executor;

    private final NoDirectDependentCarrierConverter nddCarrierConverter;

    public RabbitMQRemoteWorkerDaemon(AmqpAdmin admin,
                                      		  RabbitMQRemoteWorkerRegister register,
                                      		  CachingConnectionFactory factory,
                                      		  RabbitMQConfig config,
                                      		  ExecutorService executor,
                                      		  NoDirectDependentCarrierConverter nddCarrierConverter)
    {
       this.admin = admin;
       this.register = register;
       containers = Sets.newHashSet();
       this.factory = factory;
       this.config = config;
       this.executor = executor;
       this.nddCarrierConverter = nddCarrierConverter;
    }

    @Override
    public void start()
    {
        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to start...");
        }

        register.initialize();

        for (final String group : register.getRemoteGroups()) {
            // declare remote worker exchange for each remote method group
            final Exchange exchange = new DirectExchange(register.getRemoteExchange(group), true, false);
            admin.declareExchange(exchange);

            // declare queue for each remote method group
            final Queue queue = new Queue(register.getRemoteQueue(group), true, false, false);
            admin.declareQueue(queue);

            for (final String method : register.getRemoteMethods(group)) {
                // declare binding for each remote method
                final Binding binding = BindingBuilder.bind(queue).to(exchange).with(method).noargs();
                admin.declareBinding(binding);

                // create message listener adapter
                final SimpleMessageListenerAdapater adapter = new SimpleMessageListenerAdapater();
                adapter.setDefaultListenerMethod(SimpleMessageSubscriber.DEFAULT_METHOD);
                adapter.setMessageConverter(new HessianMessageConverter());
                final RabbitMQRemoteWorkerSubscriber subscriber =
                		new RabbitMQRemoteWorkerSubscriber(register, executor, nddCarrierConverter);
                adapter.setDelegate(subscriber);

                // create message listener container
                final SimpleMessageProcessorContainer container = new SimpleMessageProcessorContainer(factory, executor, config);
                container.setFailureListener(new RabbitMQFailureListener() {
                    @Override
                    public void onFailure(Throwable cause) {
                        if (LOG.isInfoEnabled()) {
                            LOG.info(ME  + " does not need to recover messaging infrastructure.");
                        }
                    }
                });
                container.setQueues(queue);
                container.setMessageListener(adapter);
                containers.add(container);

                if (LOG.isInfoEnabled()) {
                    LOG.info(ME + " registered message subsciber for remote method: [" + group + "]" + "." + method);
                }
            }
        }

        for (final SimpleMessageProcessorContainer container : containers) {
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
        executor.shutdown();
    }

}