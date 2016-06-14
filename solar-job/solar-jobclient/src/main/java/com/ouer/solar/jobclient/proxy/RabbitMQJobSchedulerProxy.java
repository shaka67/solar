/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient.proxy;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.google.common.base.Preconditions;
import com.ouer.solar.jobclient.Job;
import com.ouer.solar.jobclient.JobRequestTypes;
import com.ouer.solar.jobclient.JobScheduler;
import com.ouer.solar.jobclient.JobStatus;
import com.ouer.solar.rabbitmq.RabbitMQAdmin;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQManager;

/**
 * Stub of {@link JobScheduler} used for communicating job server with
 * underlying {@link RabbitTemplate}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQJobSchedulerProxy implements JobScheduler {

//    private final RabbitTemplate template;

    private final RabbitMQManager manager;

    public RabbitMQJobSchedulerProxy(RabbitMQManager manager) {
        this.manager = manager;
    }

    @Override
    public JobStatus schedule(Job job) {
    	final String appId = job.getAppId();
    	Preconditions.checkState(appId != null, "appId must not be null");
    	final RabbitTemplate template = ((RabbitMQAdmin) manager.getAmqpAdmin(appId)).getTemplate();

        final JobRequestTypes type = JobRequestTypes.JOB_ENQUEUE_REQUEST;
        final JobStatus status = (JobStatus) template.convertSendAndReceive(type.exchange(), type.routing(), job);
        return status;
    }

    @Override
    public JobStatus reschedule(Job job) {
    	final String appId = job.getAppId();
    	Preconditions.checkState(appId != null, "appId must not be null");
    	final RabbitTemplate template = ((RabbitMQAdmin) manager.getAmqpAdmin(appId)).getTemplate();

        final JobRequestTypes type = JobRequestTypes.JOB_REQUEUE_REQUEST;
        final JobStatus status = (JobStatus) template.convertSendAndReceive(type.exchange(), type.routing(), job);
        return status;
    }

    @Override
    public JobStatus delete(Job job) {
    	final String appId = job.getAppId();
    	Preconditions.checkState(appId != null, "appId must not be null");
    	final RabbitTemplate template = ((RabbitMQAdmin) manager.getAmqpAdmin(appId)).getTemplate();

        final JobRequestTypes type = JobRequestTypes.JOB_DEQUEUE_REQUEST;
        final JobStatus status = (JobStatus) template.convertSendAndReceive(type.exchange(), type.routing(), job);
        return status;
    }

    @Override
    public JobStatus pause(Job job) {
    	final String appId = job.getAppId();
    	Preconditions.checkState(appId != null, "appId must not be null");
    	final RabbitTemplate template = ((RabbitMQAdmin) manager.getAmqpAdmin(appId)).getTemplate();

        final JobRequestTypes type = JobRequestTypes.JOB_PAUSE_REQUEST;
        final JobStatus status = (JobStatus) template.convertSendAndReceive(type.exchange(), type.routing(), job);
        return status;
    }

    @Override
    public JobStatus resume(Job job) {
    	final String appId = job.getAppId();
    	Preconditions.checkState(appId != null, "appId must not be null");
    	final RabbitTemplate template = ((RabbitMQAdmin) manager.getAmqpAdmin(appId)).getTemplate();

        final JobRequestTypes type = JobRequestTypes.JOB_RESUME_REQUEST;
        final JobStatus status = (JobStatus) template.convertSendAndReceive(type.exchange(), type.routing(), job);
        return status;
    }

}