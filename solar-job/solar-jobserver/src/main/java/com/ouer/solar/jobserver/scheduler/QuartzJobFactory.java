/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Guiced {@link JobFactory} used to dynamically instantiate {@link Job} instance
 * with Guice {@link Injector}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QuartzJobFactory implements JobFactory, ApplicationContextAware {

	private ApplicationContext context;

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        final JobDetail detail = bundle.getJobDetail();
        final Class<? extends Job> jobClass = detail.getJobClass();
        return context.getBean(jobClass);
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

}