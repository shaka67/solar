/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.scheduler;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.jobclient.Job;

/**
 * Interpret {@link Job} into Quartz scheduling artifacts, such as {@link JobDetail},
 * {@link JobDataMap}, {@link Trigger}, etc.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QuartzJobInterpreter {

	private static final Logger LOG = LoggerFactory.getLogger(QuartzJobInterpreter.class);

    private JobDataMap map;
    private JobDetail detail;
    private Trigger trigger;

    private final Job job;

    public QuartzJobInterpreter(Job job) {
        this.job = job;
    }

    protected void interpretJobDataMap() {
        map = new JobDataMap();

        map.put(QuartzQueuedJob.QUEUED_JOB_APPID, job.getAppId());
        map.put(QuartzQueuedJob.QUEUED_JOB_NAME, job.getName());
        map.put(QuartzQueuedJob.QUEUED_JOB_GROUP, job.getGroup());
        map.put(QuartzQueuedJob.QUEUED_JOB_VERSION, job.getVersion());
        map.put(QuartzQueuedJob.QUEUED_JOB_ARGUMENTS, job.getArguments());
    }

    protected void interpretJobDetail()
    {
        final JobBuilder builder = JobBuilder.newJob()
                                       .ofType(QuartzQueuedJob.class)
//                                       .storeDurably(true)
                                       .storeDurably(job.isDurability())
                                       .withIdentity(getJobKey())
                                       .withDescription(job.getDescription())
                                       .usingJobData(map);
        detail = builder.build();
    }

    protected void interpretJobTrigger()
    {
    	Date startTime = job.getStartTime();
    	final Date now = new Date();
    	if (startTime != null && startTime.before(now)) {
    		LOG.warn("the start time is {}, which before now time for the job {}", startTime, job);
    		startTime = now;
    	}
        final TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
                                                        .forJob(getJobKey())
                                                        .withIdentity(getTriggerKey())
                                                        .withDescription(job.getDescription())
                                                        .startAt(startTime)
                                                        .endAt(job.getEndTime());
        if (job.hasCron()) {
//            try {
                builder.withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()));
//            } catch (final ParseException e) {
//                LOG.error(job_.getCron() + " cannot be recognized as a valid cron expression.");
//                LOG.error(e.getMessage(), e);
//            }
        } else {
            builder.withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                      .withRepeatCount(job.getRepeatCount())
                                                      .withIntervalInSeconds(job.getRepeatIntervalInSeconds()));
        }

        trigger = builder.build();
    }

    public JobDataMap getJobDataMap() {
        if (map == null) {
            interpretJobDataMap();
        }
        return map;
    }

    public JobDetail getJobDetail() {
        if (detail == null) {
            interpretJobDataMap();
            interpretJobDetail();
        }
        return detail;
    }

    public JobKey getJobKey() {
        return new JobKey(job.getKey(), job.getGroup());
    }

    public Trigger getTrigger() {
        if (trigger == null) {
            interpretJobTrigger();
        }
        return trigger;
    }

    public TriggerKey getTriggerKey() {
        return new TriggerKey(job.getKey(), job.getGroup());
    }

}