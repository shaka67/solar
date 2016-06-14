/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.google.common.base.Preconditions;
import com.ouer.solar.remote.RemoteErrorCode;
import com.ouer.solar.remote.RemoteException;
import com.ouer.solar.remote.RemoteStatus;
import com.rabbitmq.client.Channel;

/**
 * Core underlying service used to transmit RPC invocation data via RabbitMQ message.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteWorkerClient implements RabbitMQRemoteWorkerInterface {

	private final static Log LOG = LogFactory.getLog(RabbitMQRemoteWorkerClient.class);

    private final RabbitTemplate template;

    public RabbitMQRemoteWorkerClient(RabbitTemplate template)
    {
        this.template = template;
    }

    /**
     * Invoke remote method with RabbitMQ message.
     *
     * @param group The group name of the remote method
     * @param method The name of the remote method
     * @param arguments The method arguments of the remote method
     * @throws RemoteException
     */
    public RemoteStatus invoke(String group, String method, Object[] arguments) throws RemoteException
    {
        Preconditions.checkArgument(group != null, "Remote method group is required");
        Preconditions.checkArgument(method != null, "Remote method name is required");
        Preconditions.checkArgument(arguments != null, "Remote method arguments are required");

        final String exchange = getRemoteWorkerExchange(group);

        final boolean existed = template.execute(new ChannelCallback<Boolean>() {
            @Override
            public Boolean doInRabbit(Channel channel) throws Exception {
                try {
                    channel.exchangeDeclarePassive(exchange);
                } catch (final IOException e) {
                    LOG.error("[" + exchange + "] does not exist. Invocation has to be aborted.");
                    return false;
                }
                return true;
            }
        });

        if (!existed) {
            final String error = "[" + exchange + "] does not exist.";
            throw new RemoteException(error, RemoteErrorCode.NO_DECLARED_EXCHANGE);
        }

        Object response;

        try {
            response = template.convertSendAndReceive(exchange, method, arguments);
        } catch (final AmqpException e) {
            final String error = "[AmqpException] Failed to invoke remote method because of: " + e.getMessage();
            LOG.error(error);
            throw new RemoteException(error, RemoteErrorCode.UNKNOWN_EXCEPTION, e);
        }

        if (response == null) {
            final String error = "[Timeout] Failed to get invocation response from remote worker: " + group + "." + method;
            LOG.error(error);
            throw new RemoteException(error, RemoteErrorCode.INVOKE_REMOTE_METHOD_TIMEOUT);
        }

        return (RemoteStatus) response;
    }

    private String getRemoteWorkerExchange(String group)
    {
        return DEFAULT_REMOTE_WORKER_EXCHANGE_PREFIX + group;
    }

}