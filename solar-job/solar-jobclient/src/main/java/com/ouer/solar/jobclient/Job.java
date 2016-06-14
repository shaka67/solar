/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.ouer.solar.able.AppConfigurable;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrier;

/**
 * Job is a data transfer object (DTO) providing information that would be eventually
 * read by JobServer. The unique identifiable key of a job instance is comprised by
 * name, user, version and group. Multiple jobs can share the same name with different version
 * or different user, which means that multiple job instances can share the same name but for
 * different target user. Version is used to control the update history of the job.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Job implements Serializable, NoDirectDependentCarrier, AppConfigurable {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_JOB_GROUP = "DEFAULT";
    private static final String DEFAULT_JOB_DESCRIPTION = "";
    private static final String DEFAULT_JOB_VERSION = "1.0";

    private final String name;
    private final String user;
    private String group;
    private String version;
    private String description;
    private Object[] arguments;
    private String cron;
    private long repeatCount;
    private long repeatInterval;
    private Date startTime;
    private Date endTime;
    private TimeUnit timeUnit;
    /**
     * Whether or not the <code>Job</code> should remain stored after it is
     * orphaned.
     */
    private boolean durability;

    /**
     * Use this field to distinguish different job configurations
     */
    private final String appId;

    /**
     * @param name The name of a job
     * @param user The user who would use this job for a particular task
     */
    public Job(String appId, String name, String user) {
    	this.appId = appId;
        this.name = name;
        this.user = user;
        defaults();
    }

    private void defaults() {
        group = DEFAULT_JOB_GROUP;
        version = DEFAULT_JOB_VERSION;
        description = DEFAULT_JOB_DESCRIPTION;
        repeatCount = 0;
        repeatInterval = 0;
        startTime = new Date();
        timeUnit = TimeUnit.SECONDS;
        durability = true;
    }

    /**
     * Set the group (source) of this job
     * @param source The group of this job, which is same with the source.
     */
    public Job withGroup(String group) {
    	this.group = group;
        return this;
    }

    /**
     * Set the source (group) of this job
     * @param source The source of this job, which is same with the group.
     */
    public Job withSource(String source) {
        group = source;
        return this;
    }

    public Job withDescription(String description) {
    	this.description = description;
        return this;
    }

    public Job withVersion(String version) {
    	this.version = version;
        return this;
    }

    public Job withArgument(Object[] arguments) {
    	this.arguments = arguments;
        return this;
    }

    public Job withCron(String cron) {
    	this.cron = cron;
        return this;
    }

    public Job withRepeatCount(long repeatCount) {
    	this.repeatCount = repeatCount;
        return this;
    }

    public Job withRepeatForever() {
    	this.repeatCount = -1;
        return this;
    }

    public Job withRepeatInterval(long repeatInterval, TimeUnit timeUnit) {
    	this.repeatInterval = repeatInterval;
    	this.timeUnit = timeUnit;
        return this;
    }

    public Job withRepeatIntervalInSeconds(long repeatIntervalInSeconds) {
    	this.repeatInterval = repeatIntervalInSeconds;
        this.timeUnit = TimeUnit.SECONDS;
        return this;
    }

    public Job withRepeatIntervalInMinutes(long repeatIntervalInMinutes) {
    	this.repeatInterval = repeatIntervalInMinutes;
    	this.timeUnit = TimeUnit.MINUTES;
        return this;
    }

    public Job withStartTime(Date startTime) {
    	this.startTime = startTime;
        return this;
    }

    public Job withEndTime(Date endTime) {
    	this.endTime = endTime;
        return this;
    }

    public Job withDurability(boolean durability) {
    	this.durability = durability;
    	return this;
    }

    /**
     * @return The name of this job
     */
    public String getName() {
        return name;
    }

    /**
     * @return The group of this job
     */
    public String getGroup() {
        return group;
    }

    /**
     * @return The description of this job
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The user of this job
     */
    public String getUser() {
        return user;
    }

    /**
     * @return The version of this job
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return The unique identifiable key of this job, in format: [name.user].version
     */
    public String getKey() {
        return "[" + name + "." + user  + "]" + "." + version;
    }

    /**
     * @return The user provided arguments for running a particular task
     */
	public Object[] getArguments() {
        return arguments;
    }

    public String getCron() {
        return cron;
    }

    public boolean hasCron() {
        return cron != null && !cron.isEmpty();
    }

    public int getRepeatCount() {
        return (int) repeatCount;
    }

    public int getRepeatInterval() {
        return (int) repeatInterval;
    }

    public int getRepeatIntervalInSeconds() {
        return (int) TimeUnit.SECONDS.convert(repeatInterval, timeUnit);
    }

    public int getRepeatIntervalInMinutes() {
        return (int) TimeUnit.MINUTES.convert(repeatInterval, timeUnit);
    }

    public boolean isRepeatForever() {
        return repeatCount < 0;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public boolean isDurability() {
    	return durability;
    }

    @Override
	public String getAppId() {
		return appId;
	}

	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[")
               .append("name: ").append(name).append(", ")
               .append("user: ").append(user).append(", ")
               .append("group: ").append(group).append(", ")
               .append("version: ").append(version)
               .append("]");
        return builder.toString();
    }


	@Override
	public Object[] getNoDirectDependentMsgs() {
		return arguments;
	}

	@Override
	public void setNoDirectDependentMsgs(Object[] msgs) {
		this.arguments = msgs;
	}

    @Override
	public Job clone() {
    	final Job copy = new Job(appId, name, user);
    	copy.cron = cron;
    	copy.description = description;
    	copy.endTime = endTime;
    	copy.group = group;
    	copy.repeatCount = repeatCount;
    	copy.repeatInterval = repeatInterval;
    	copy.startTime = startTime;
    	copy.timeUnit = timeUnit;
    	copy.version = version;
    	copy.durability = durability;
    	return copy;
    }

}