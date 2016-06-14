/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.jobserver.workflow.WorkflowLauncher;

/**
 * Execute Quartz {@link Job} and launch job workflow instance with {@link WorkflowLauncher}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QuartzQueuedJob implements Job {

	public static final String QUEUED_JOB_APPID = "queued.job.appId";
    public static final String QUEUED_JOB_NAME = "queued.job.name";
    public static final String QUEUED_JOB_GROUP = "queued.job.group";
    public static final String QUEUED_JOB_VERSION = "queued.job.version";
    public static final String QUEUED_JOB_ARGUMENTS = "queued.job.arguments";

    private static final String ME = "[QueuedJob]";
    private static final Logger LOG = LoggerFactory.getLogger(QuartzQueuedJob.class);

    private final WorkflowLauncher launcher;

    public QuartzQueuedJob(WorkflowLauncher launcher)
    {
        this.launcher = launcher;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        final JobDataMap map = context.getJobDetail().getJobDataMap();
        final String appId = (String) map.get(QUEUED_JOB_APPID);
        final String group = (String) map.get(QUEUED_JOB_GROUP);
        final String name = (String) map.get(QUEUED_JOB_NAME);
        String version = (String) map.get(QUEUED_JOB_VERSION);
        if (version == null) {
            version = "1.0"; // not sure if this protection is needed
        }
        Object[] arguments = (Object[]) map.get(QUEUED_JOB_ARGUMENTS);
        if (arguments == null) {
            arguments = new Object[0];
        }

        try {
            launcher.launch(appId, group, name, version, arguments);
        } catch (final Throwable e) {
            if (context.getFireTime() != null) {
                final String firetime = getDateFormat().format(context.getFireTime());
                LOG.error(ME + " failed to execute queued job at " + firetime + " for: " + group + "." + name);
            }
            if (context.getNextFireTime() != null) {
                final String nexttime = getDateFormat().format(context.getNextFireTime());
                LOG.error(ME + " The next fire time would be: " + nexttime);
            }
            Throwable cause = e.getCause();
            if (cause == null) {
                cause = e;
            }
            LOG.error(ME + " The root cause of this error is: " + e.getMessage(), cause);
            throw new JobExecutionException(cause, false);
        }
    }

    DateFormat getDateFormat()
    {
        return new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    }

}