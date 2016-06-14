/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.scheduler;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Monitor;
import com.ouer.solar.jobclient.Job;
import com.ouer.solar.jobclient.JobScheduler;
import com.ouer.solar.jobclient.JobStatus;

/**
 * Schedule received job request into job repository with Quartz {@link Scheduler}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QuartzJobScheduler implements JobScheduler {

	private static final String ME = "[JobScheduler]";
	private static final Logger LOG = LoggerFactory.getLogger(QuartzJobScheduler.class);

    private final Monitor monitor;
    private final Scheduler scheduler;

    public QuartzJobScheduler(Scheduler scheduler)
    {
        monitor = new Monitor();
        this.scheduler = scheduler;
    }

    @Override
    public JobStatus schedule(Job job)
    {
        final QuartzJobInterpreter interpreter = new QuartzJobInterpreter(job);
        final JobKey key = interpreter.getJobKey();
        try {
            monitor.enter();
            if (scheduler.checkExists(key)) {
                LOG.warn(ME + " already has scheduled job for " + key);
                LOG.warn(ME + " would ignore this schedule request.");
                return JobStatus.withFailedStatusCode().withStatusText("Job already scheduled");
            }
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " is going to schedule job " + key);
            }
            scheduler.scheduleJob(interpreter.getJobDetail(), interpreter.getTrigger());
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " scheduled job " + key);
            }
            return JobStatus.withSucceedStatusCode();
        } catch (final SchedulerException e) {
            LOG.error(ME + " failed to schedule job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("SchedulerException: " + e.getMessage());
        } catch (final Exception e) {
            LOG.error(ME + " failed to schedule job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("Exception: " + e.getMessage());
        } finally {
            monitor.leave();
        }
    }

    @Override
    public JobStatus reschedule(Job job)
    {
        final QuartzJobInterpreter interpreter = new QuartzJobInterpreter(job);
        final JobKey key = interpreter.getJobKey();
        try {
            monitor.enter();
            if (!scheduler.checkExists(key)) {
//                LOG.warn(ME + " does not have scheduled job for " + key);
//                LOG.warn(ME + " would ignore this reschedule request.");
//                return JobStatus.withFailedStatusCode().withStatusText("Job was not scheduled yet");
                if (LOG.isInfoEnabled()) {
                    LOG.info(ME + " is going to schedule job " + key + " first time");
                }
                scheduler.scheduleJob(interpreter.getJobDetail(), interpreter.getTrigger());
                if (LOG.isInfoEnabled()) {
                    LOG.info(ME + " scheduled job " + key + " first time");
                }
                return JobStatus.withSucceedStatusCode();
            }
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " is going to reschedule job " + key);
            }
            scheduler.deleteJob(key);
            scheduler.scheduleJob(interpreter.getJobDetail(), interpreter.getTrigger());
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " rescheduled job " + key);
            }
            return JobStatus.withSucceedStatusCode();
        } catch (final SchedulerException e) {
            LOG.error(ME + " failed to reschedule job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("SchedulerException: " + e.getMessage());
        } catch (final Exception e) {
            LOG.error(ME + " failed to reschedule job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("Exception: " + e.getMessage());
        } finally {
            monitor.leave();
        }
    }

    @Override
    public JobStatus delete(Job job)
    {
        final QuartzJobInterpreter interpreter = new QuartzJobInterpreter(job);
        final JobKey key = interpreter.getJobKey();
        try {
            monitor.enter();
            if (!scheduler.checkExists(key)) {
                LOG.warn(ME + " does not have scheduled job for " + key);
                LOG.warn(ME + " would ignore this delete request.");
                return JobStatus.withFailedStatusCode().withStatusText("Job was not scheduled yet");
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("[scheduler] is going to delete job " + key);
            }
            scheduler.deleteJob(key);
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " deleted job " + key);
            }
            return JobStatus.withSucceedStatusCode();
        } catch (final SchedulerException e) {
            LOG.error(ME + " failed to delete job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("SchedulerException: " + e.getMessage());
        } catch (final Exception e) {
            LOG.error(ME + " failed to delete job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("Exception: " + e.getMessage());
        } finally {
            monitor.leave();
        }
    }

    @Override
    public JobStatus pause(Job job)
    {
        final QuartzJobInterpreter interpreter = new QuartzJobInterpreter(job);
        final JobKey key = interpreter.getJobKey();
        try {
            monitor.enter();
            if (!scheduler.checkExists(key)) {
                LOG.warn(ME + " does not have scheduled job for " + key);
                LOG.warn(ME + " would ignore this pause request.");
                return JobStatus.withFailedStatusCode().withStatusText("Job was not scheduled yet");
            }
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " is going to pause job " + key);
            }
            scheduler.pauseJob(key);
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " paused job " + key);
            }
            return JobStatus.withSucceedStatusCode();
        } catch (final SchedulerException e) {
            LOG.error(ME + " failed to pause job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("SchedulerException: " + e.getMessage());
        } catch (final Exception e) {
            LOG.error(ME + " failed to pause job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("Exception: " + e.getMessage());
        } finally {
            monitor.leave();
        }
    }

    @Override
    public JobStatus resume(Job job)
    {
        final QuartzJobInterpreter interpreter = new QuartzJobInterpreter(job);
        final JobKey key = interpreter.getJobKey();
        try {
            monitor.enter();
            if (!scheduler.checkExists(key)) {
                LOG.warn(ME + " does not have scheduled job for " + key);
                LOG.warn(ME + " would ignore this resume request.");
                return JobStatus.withFailedStatusCode().withStatusText("Job was not scheduled yet");
            }
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " is going to resume job " + key);
            }
            scheduler.resumeJob(key);
            if (LOG.isInfoEnabled()) {
                LOG.info(ME + " resumed job " + key);
            }
            return JobStatus.withSucceedStatusCode();
        } catch (final SchedulerException e) {
            LOG.error(ME + " failed to resume job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("SchedulerException: " + e.getMessage());
        } catch (final Exception e) {
            LOG.error(ME + " failed to resume job: " + job);
            LOG.error("The root cause of this error is: " + e.getMessage(), e);
            return JobStatus.withFailedStatusCode().withStatusText("Exception: " + e.getMessage());
        } finally {
            monitor.leave();
        }
    }

}