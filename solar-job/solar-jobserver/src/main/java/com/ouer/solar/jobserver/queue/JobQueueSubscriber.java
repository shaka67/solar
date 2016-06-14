/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.jobclient.Job;
import com.ouer.solar.jobclient.JobRequestTypes;
import com.ouer.solar.jobclient.JobStatus;
import com.ouer.solar.jobserver.scheduler.QuartzJobScheduler;
import com.ouer.solar.rabbitmq.SimpleMessage;
import com.ouer.solar.rabbitmq.SimpleMessageSubscriber;

/**
 * Message subscriber for processing Job requests.
 * NOTE that {@link JobQueueSubscriber} is singleton, meaning we can process only one
 * job request at a time.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobQueueSubscriber implements SimpleMessageSubscriber<JobStatus> {

	private static final String ME = "[JobQueue Subscriber]";
    private static final Logger LOG = LoggerFactory.getLogger(JobQueueSubscriber.class);

    private final QuartzJobScheduler scheduler;

    public JobQueueSubscriber(QuartzJobScheduler scheduler)
    {
        this.scheduler = scheduler;
    }

    @Override
    public JobStatus onMessage(SimpleMessage message)
    {
        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " received job request from: " + message.getMessageId());
        }

        final String routingKey = message.getRoutingKey();
        final JobRequestTypes requestType = JobRequestTypes.fromRoutingKey(routingKey);
        if (requestType == null) {
            LOG.warn(ME + " cannot determine job request type for: " + message.getMessageId());
            return JobStatus.withFailedStatusCode().withStatusText("cannot determine job request type");
        }

        final Object content = message.getContent();
        if (!(content instanceof Job)) {
            LOG.warn(ME + " illegal job request argument: " + message.getMessageId());
            return JobStatus.withFailedStatusCode().withStatusText("illegal job request argument");
        }
        final Job job = (Job) content;

        JobStatus status;
        switch (requestType) {
        case JOB_ENQUEUE_REQUEST:
            status = scheduler.schedule(job);
            break;
        case JOB_REQUEUE_REQUEST:
            status = scheduler.reschedule(job);
            break;
        case JOB_DEQUEUE_REQUEST:
            status = scheduler.delete(job);
            break;
        case JOB_PAUSE_REQUEST:
            status = scheduler.pause(job);
            break;
        case JOB_RESUME_REQUEST:
            status = scheduler.resume(job);
            break;
        default:
            status = JobStatus.withFailedStatusCode().withStatusText("unknown job request type: " + requestType);
            break;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " finished job request [" + requestType + "] for: " + job.getAppId() + " -- " + job.getKey());
        }

        return status;
    }

}