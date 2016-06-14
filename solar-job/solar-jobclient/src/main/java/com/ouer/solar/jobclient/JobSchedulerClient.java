/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

import com.ouer.solar.annotation.Logging;

/**
 * Convenient utility class used to connect job server with {@link JobScheduler}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobSchedulerClient {

    private final JobScheduler scheduler;

    public JobSchedulerClient(JobScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Logging
    public JobStatus scheduleJob(Job job) {
        return scheduler.schedule(job);
    }

    @Logging
    public JobStatus rescheduleJob(Job job) {
        return scheduler.reschedule(job);
    }

    @Logging
    public JobStatus deleteJob(Job job) {
        return scheduler.delete(job);
    }

    @Logging
    public JobStatus pauseJob(Job job) {
        return scheduler.pause(job);
    }

    @Logging
    public JobStatus resumeJob(Job job) {
        return scheduler.resume(job);
    }

}