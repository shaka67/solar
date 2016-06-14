/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.ouer.solar.rabbitmq.SimpleMessage;
import com.ouer.solar.rabbitmq.SimpleMessageSubscriber;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrier;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;
import com.ouer.solar.remote.RemoteErrorCode;
import com.ouer.solar.remote.RemoteStatus;

/**
 * Received all messages from delegated remote service queues then invoke corresponding
 * remote method implementation in terms of messages routing key.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerSubscriber implements SimpleMessageSubscriber<RemoteStatus> {

	private final static String ME = "[RemoteWorker Subscirber]";
    private final static Log LOG = LogFactory.getLog(RabbitMQRemoteWorkerSubscriber.class);

    private final RabbitMQRemoteWorkerRegister register;
    private final ListeningExecutorService executor;
    private final NoDirectDependentCarrierConverter nddCarrierConverter;

    public RabbitMQRemoteWorkerSubscriber(RabbitMQRemoteWorkerRegister register,
                                          		 ExecutorService executor,
                                          		 NoDirectDependentCarrierConverter nddCarrierConverter)
    {
        this.register = register;
        this.executor = MoreExecutors.listeningDecorator(executor);
        this.nddCarrierConverter = nddCarrierConverter;
    }

    @Override
    public RemoteStatus onMessage(final SimpleMessage message)
    {
        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " received message from: " + message.getMessageId());
        }

        final String exchange = message.getExchangeName();
        final String routing = message.getRoutingKey();

        final String group = register.getRemoteGroup(exchange);
        if (group.isEmpty()) {
            final String error = ME + " cannot recognize remote method for: " + message.getMessageId();
            LOG.warn(error);
            return RemoteStatus.withFailedStatusCode()
                               .withErrorMessage(error)
                               .withErrorCode(RemoteErrorCode.NO_REGISTERED_REMOTE_GROUP);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " recognized remote group for: " + group);
        }

        final String method = register.getRemoteMethod(routing);
        if (method.isEmpty()) {
            final String error = ME + " cannot determine remote method name for: " + message.getMessageId();
            LOG.warn(error);
            return RemoteStatus.withFailedStatusCode()
                               .withErrorMessage(error)
                               .withErrorCode(RemoteErrorCode.NO_REGISTERED_REMOTE_METHOD);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " recognized remote method for: " + method);
        }

        final Object target = register.getRemoteWorker(group);
        if (target == null) {
            final String error = ME + " cannot find registered remote method for: " + message.getMessageId();
            LOG.warn(error);
            return RemoteStatus.withFailedStatusCode()
                               .withErrorMessage(error)
                               .withErrorCode(RemoteErrorCode.NO_REGISTERED_REMOTE_SERVICE);
        }

        final Object[] arguments;
        final Object content = message.getContent();
        if (!(content instanceof Object[])) {
            arguments = new Object[] { content };
        }
        else if (((Object[]) content).length == 0) {
        	arguments = (Object[]) content;
        }
        else {
        	final Object[] tmpArgs = (Object[]) content;
        	if (NoDirectDependentCarrier.class.equals(tmpArgs[0])) {
        		try {
					arguments = nddCarrierConverter.reverse(tmpArgs);
				} catch (final Exception e) {
					final String error = ME + " cannot complete NDD carrier convert due to " + e;
					LOG.error(error);
					return RemoteStatus.withFailedStatusCode()
							.withErrorMessage(error)
							.withErrorCode(RemoteErrorCode.NO_SUCH_REMOTE_METHOD_ON_TARGET);
				}
        	}
        	else {
        		arguments = (Object[]) content;
        	}
        }
        final ListenableFuture<Object> future = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return MethodUtils.invokeMethod(target, method, arguments);
            }
        });
        final FutureCallback<Object> callback = new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                if (LOG.isInfoEnabled()) {
                    LOG.info(ME + " finished remote method invocation by target worker for: " + message.getMessageId());
                }
            }
            @Override
            public void onFailure(Throwable e) {
                LOG.error(ME + " got " + e.getMessage() + " for " + message.getMessageId());
                final Throwable cause = e.getCause();
                if (cause != null) {
                    LOG.error(cause.getMessage(), cause);
                } else {
                    LOG.error(e.getMessage(), e);
                }
            }
        };
        Futures.addCallback(future, callback);

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " dispatched remote method invocation to target worker for: " + message.getMessageId());
        }

        return RemoteStatus.withSucceedStatusCode();
    }

}