/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;

import com.google.common.util.concurrent.Monitor;
import com.ouer.solar.Sleeper;
import com.rabbitmq.client.Channel;

/**
 * Encapsulate core {@link BlockingQueueConsumer} and manage its life cycle
 * with sophisticated error handling mechanism.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleMessageProcessor implements Callable<Boolean> {

    private final static Log LOG = LogFactory.getLog(SimpleMessageProcessor.class);
    
    private final Monitor monitor;
    private final BlockingQueueConsumer consumer;
    private final SimpleMessageProcessorContainer container;
    
    public SimpleMessageProcessor(BlockingQueueConsumer consumer, SimpleMessageProcessorContainer container)
    {
        this.consumer = consumer;
        this.container = container;
        monitor = new Monitor();
    }

    @Override
    public Boolean call() throws Exception
    {
        boolean aborted = false;

        try {
            doStart();
            doCall();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            if (LOG.isInfoEnabled()) {
                LOG.info("Message consumer thread interrupted, processing stopped");
            }
            aborted = true;
        } catch (final Exception e) {
            LOG.error("Message consumer raised exception: " + e.getMessage());
            LOG.error("Message processor can restart the consumer if the recovery mechanism is enabled.");
            throw e;
        } finally {
            doShutdown();
        }

        if (aborted) {
            LOG.warn("Message consumer is aborted");
        }

        return aborted;
    }

    protected void doStart() throws Exception
    {
        if (LOG.isInfoEnabled()) {
            LOG.info("Attempt to start message consumer: " + consumer);
        }

        try {
            monitor.enter();
            consumer.start();
        } catch (final Exception e) {
            Sleeper.sleep(container.getRecoveryInterval(), TimeUnit.MILLISECONDS);
            throw e;
        } finally {
            monitor.leave();
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Started message consumer: " + consumer);
        }
    }

    protected void doShutdown()
    {
        if (LOG.isInfoEnabled()) {
            LOG.info("Attempt to shutdown message consumer: " + consumer);
        }

        try {
            monitor.enter();
            consumer.stop();
        } catch (final Exception e) {
            LOG.error("Could not shutdown message consumer", e);
        } finally {
            // Ensure consumer counts are correct
            // (another is going to start because of the exception, but we haven't counted down yet)
            container.removeMessageConsumer(consumer);
            container.removeMessageProcessor(this);
            monitor.leave();
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Terminated message consumer: " + consumer);
        }
    }

    protected void doCall() throws Exception
    {
        // Always better to stop receiving as soon as possible if the message consumer is transactional
        boolean continuable = false;
        while (container.isActive() || continuable) {
            final Channel channel = consumer.getChannel();
            for (int i = 0; i < container.getPrefetchCount(); i++) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Waiting for message from consumer");
                }
                try {
                    final Message message = consumer.nextMessage(container.getReceiveTimeout());
                    if (message == null) {
                        break;
                    }
                    container.onMessage(channel, message);
                } catch (final ImmediateAcknowledgeAmqpException e) {
                    LOG.error("Occurred immediate acknowledge amqp exception: " + e.getMessage());
                    break;
                } catch (final ListenerExecutionFailedException e) {
                    LOG.error("Occurred listener execution failed exception: " + e.getMessage());
                    break;
                } catch (final Throwable e) {
                    consumer.rollbackOnExceptionIfNecessary(e);
                    throw new Exception(e);
                }
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Finished processing message from consumer");
                }
            }
            // Will come back false when the queue is drained
            continuable = container.commitMessageConsumer(consumer);
        }
    }

}