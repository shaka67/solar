/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.ActiveObjectCounter;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.MoreExecutors;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.rabbitmq.RabbitMQFailureListener;
import com.rabbitmq.client.Channel;

/**
 * Manager a set of {@link BlockingQueueConsumer} and use {@link SimpleMessageProcessor}
 * to process incoming {@link Message}. The received {@link} would eventually be forwarded
 * to registered message listeners.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleMessageProcessorContainer extends AbstractMessageListenerContainer {

    public final static long DEFAULT_RECEIVE_TIMEOUT = 1000L;
    public final static long DEFAULT_RECOVERY_INTERVAL = 5000L;
    public final static long DEFAULT_STARTUP_TIMEOUT = 60000L;
    public final static long DEFAULT_SHUTDOWN_TIMEOUT = 5000L;

    private final static Log LOG = LogFactory.getLog(SimpleMessageProcessorContainer.class);

    private RabbitMQFailureListener recoverer;

    private final AtomicInteger prefetchCount;
    private final AtomicInteger concurrentConsumers;
    private final AtomicLong receiveTimeout;
    private final AtomicLong recoveryInterval;
    private final AtomicLong startupTimeout;
    private final AtomicLong shutdownTimeout;
    private final AtomicBoolean defaultRequeueRejected;

    private final Monitor monitor;
    private final ListeningExecutorService executor;
    private final MessagePropertiesConverter converter;
    private final Set<SimpleMessageProcessor> processors;
    private final Set<BlockingQueueConsumer> consumers;
    private final ActiveObjectCounter<BlockingQueueConsumer> counter;

    public SimpleMessageProcessorContainer(ConnectionFactory factory, ExecutorService executor)
    {
        prefetchCount = new AtomicInteger(1);
        concurrentConsumers = new AtomicInteger(1);
        receiveTimeout = new AtomicLong(DEFAULT_RECEIVE_TIMEOUT);
        recoveryInterval = new AtomicLong(DEFAULT_RECOVERY_INTERVAL);
        startupTimeout = new AtomicLong(DEFAULT_STARTUP_TIMEOUT);
        shutdownTimeout = new AtomicLong(DEFAULT_SHUTDOWN_TIMEOUT);
        defaultRequeueRejected = new AtomicBoolean(true);

        setConnectionFactory(factory);
        monitor = new Monitor();
        this.executor = MoreExecutors.listeningDecorator(executor);
        converter = new DefaultMessagePropertiesConverter();
        processors = Sets.newHashSet();
        consumers = Sets.newHashSet();
        counter = new ActiveObjectCounter<BlockingQueueConsumer>();
    }

    public SimpleMessageProcessorContainer(ConnectionFactory factory,
    										   ExecutorService executor,
    										   RabbitMQConfig config)
    {
        prefetchCount = new AtomicInteger(config.getPrefetchCount());
        concurrentConsumers = new AtomicInteger(config.getConcurrentConsumers());
        receiveTimeout = new AtomicLong(config.getReceiveTimeout());
        recoveryInterval = new AtomicLong(config.getRecoveryInterval());
        startupTimeout = new AtomicLong(config.getStartupTimeout());
        shutdownTimeout = new AtomicLong(config.getShutdownTimeout());
        defaultRequeueRejected = new AtomicBoolean(config.isDefaultRequeueRejected());

        setConnectionFactory(factory);
        monitor = new Monitor();
        this.executor = MoreExecutors.listeningDecorator(executor);
        converter = new DefaultMessagePropertiesConverter();
        processors = Sets.newHashSet();
        consumers = Sets.newHashSet();
        counter = new ActiveObjectCounter<BlockingQueueConsumer>();
    }

    public void setRecoveryInterval(long recoveryInterval)
    {
    	this.recoveryInterval.set(recoveryInterval);
    }

    public long getRecoveryInterval()
    {
        return recoveryInterval.get();
    }

    public void setConcurrentConsumers(int concurrentConsumers)
    {
    	this.concurrentConsumers.set(concurrentConsumers);
    }

    public int getConcurrentConsumers()
    {
        return concurrentConsumers.get();
    }

    public void setReceiveTimeout(long receiveTimeout)
    {
    	this.receiveTimeout.set(receiveTimeout);
    }

    public long getReceiveTimeout()
    {
        return receiveTimeout.get();
    }

    public void setStartupTimeout(long startupTimeout)
    {
    	this.startupTimeout.set(startupTimeout);
    }

    public long getStartupTimeout()
    {
        return startupTimeout.get();
    }

    public void setShutdownTimeout(long shutdownTimeout)
    {
    	this.shutdownTimeout.set(shutdownTimeout);
    }

    public long getShutdownTimeout()
    {
        return shutdownTimeout.get();
    }

    public void setPrefetchCount(int prefetchCount)
    {
    	this.prefetchCount.set(prefetchCount);
    }

    public int getPrefetchCount()
    {
        return prefetchCount.get();
    }

    public void setDefaultRequeueRejected(boolean defaultRequeueRejected)
    {
    	this.defaultRequeueRejected.set(defaultRequeueRejected);
    }

    public boolean getDefaultRequeueRejected()
    {
        return defaultRequeueRejected.get();
    }

    public void setFailureListener(RabbitMQFailureListener listener)
    {
        recoverer = listener;
    }

    void removeMessageConsumer(BlockingQueueConsumer consumer)
    {
        counter.release(consumer);
        consumers.remove(consumer);
    }

    void removeMessageProcessor(SimpleMessageProcessor processor)
    {
        processors.remove(processor);
    }

    BlockingQueueConsumer createMessageConsumer()
    {
        return new BlockingQueueConsumer(getConnectionFactory(),
                                         converter,
                                         counter,
                                         getAcknowledgeMode(),
                                         isChannelTransacted(),
                                         getPrefetchCount(),
                                         getDefaultRequeueRejected(),
                                         getQueueNames());
    }

    SimpleMessageProcessor createMessageProcessor()
    {
        final BlockingQueueConsumer consumer = createMessageConsumer();
        consumers.add(consumer);
        final SimpleMessageProcessor processor = new SimpleMessageProcessor(consumer, this);
        processors.add(processor);
        return processor;
    }

    boolean commitMessageConsumer(BlockingQueueConsumer consumer) throws IOException
    {
        return consumer.commitIfNecessary(isChannelTransacted()) && !isChannelTransacted();
    }

    void onMessage(Channel channel, Message message) throws Throwable
    {
        super.executeListener(channel, message);
    }

    @Override
    protected void doInitialize() throws Exception
    {
        if (!processors.isEmpty()) {
            return;
        }

        counter.reset();

        for (int i = 0; i < getConcurrentConsumers(); i++) {
            createMessageProcessor();
        }
    }

    @Override
    protected void doStart() throws Exception
    {
        super.doStart();

        try {
            monitor.enter();
            for (final SimpleMessageProcessor processor : processors) {
                doExecute(processor);
            }
        } finally {
            monitor.leave();
        }
    }

    void restart(Throwable cause)
    {
        try {
            monitor.enter();
            if (recoverer != null) {
                recoverer.onFailure(cause);
            }
            final SimpleMessageProcessor processor = createMessageProcessor();
            doExecute(processor);
        } finally {
            monitor.leave();
        }
    }

    protected void doExecute(final SimpleMessageProcessor processor)
    {
        final ListenableFuture<Boolean> future = executor.submit(processor);
        final FutureCallback<Boolean> callback = new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aborted) {
                if (aborted) {
                    shutdown();
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                try {
                    restart(cause);
                } catch (final Throwable e) {
                    LOG.error("Unrecoverable failure on message processor restart: " + e.getMessage(), e);
                }
            }
        };
        Futures.addCallback(future, callback);
    }

    @Override
    protected void doStop()
    {
        shutdown();
    }

    @Override
    protected void doShutdown()
    {
        if (!isRunning()) {
            LOG.warn("Message process contained has been terminated already");
            return;
        }

        try {
            if (LOG.isInfoEnabled()) {
                LOG.info("Waiting for message consumers to finish.");
            }
            final boolean finished = counter.await(getShutdownTimeout(), TimeUnit.MILLISECONDS);
            if (finished) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Successfully waited for message consumers to finish.");
                }
            } else {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Message consumers are not finished. Forcing connections to close.");
                }
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.warn("Interrupted waiting for message consumers. Continuing with shutdown.");
        }

        monitor.enter();
        dispose();
        monitor.leave();
    }

    void dispose()
    {
        consumers.clear();
        processors.clear();
        executor.shutdown();
    }

}