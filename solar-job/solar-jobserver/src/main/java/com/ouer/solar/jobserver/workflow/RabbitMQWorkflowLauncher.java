/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientManager;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerClient;
import com.ouer.solar.remote.RemoteException;
import com.ouer.solar.remote.RemoteStatus;
import com.ouer.solar.retry.RetryCallback;
import com.ouer.solar.retry.RetryContext;
import com.ouer.solar.retry.RetryOperationTemplate;

/**
 * Launch workflow instance with RabbitMQ.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQWorkflowLauncher implements WorkflowLauncher {

    private static final String ME = "[JobLauncher]";
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQWorkflowLauncher.class);

//    private final RabbitMQRemoteWorkerClient client;

    private final RabbitMQWorkerClientManager manager;
    private final RetryOperationTemplate retry;

    public RabbitMQWorkflowLauncher(RabbitMQWorkerClientManager manager, RetryOperationTemplate retry)
    {
        this.manager = manager;
        this.retry = retry;
    }

    @Override
    public void launch(final String appId, final String group, final String name, final String version, final Object[] arguments) throws Throwable
    {
    	Preconditions.checkArgument(appId != null, "The group of workflow is required");
        Preconditions.checkArgument(group != null, "The group of workflow is required");
        Preconditions.checkArgument(name != null, "The name of workflow is required");
        Preconditions.checkArgument(version != null, "The version of workflow is required");
        Preconditions.checkArgument(arguments != null, "The arguments of workflow is required");

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to launch workflow instance for job: " + debug(appId, group, name, version, arguments));
        }

        final RemoteStatus status = retry.execute(new RetryCallback<RemoteStatus>() {
            @Override
            public RemoteStatus apply(RetryContext context) throws Throwable {
            	final RabbitMQRemoteWorkerClient client = manager.getWorkerClient(appId);
                return client.invoke(group, name, arguments);
            }
        });

        if (!status.isSucceed()) {
            LOG.error(ME + " had a problem to launch workflow instance for job: " + debug(appId, group, name, version, arguments));
            LOG.error(ME + " The details of this problem is: " + status.getErrorCode() + " - " + status.getErrorMessage());
            throw new RemoteException(status.getErrorMessage(), status.getErrorCode());
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " finished launching workflow instance for job: " + debug(appId, group, name, version, arguments));
        }
    }

    private String debug(String appId, String group, String name, String version, Object[] arguments)
    {
        return appId + " -- " + group + "." + name + "[" + version + "] with " + arguments.length + " arguments.";
    }

}