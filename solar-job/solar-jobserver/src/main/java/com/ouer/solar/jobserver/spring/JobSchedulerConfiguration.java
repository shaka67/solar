/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.quartz.Job;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.jobserver.scheduler.QuartzJobFactory;
import com.ouer.solar.jobserver.scheduler.QuartzQueuedJob;
import com.ouer.solar.jobserver.workflow.WorkflowLauncher;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class JobSchedulerConfiguration {

//	private static final String QUARTZ = "quartz";

	@Bean
	JobFactory jobFactory() {
		return new QuartzJobFactory();
	}

//	@Autowired
//	@Bean
//	QuartzJobScheduler jobScheduler(Scheduler scheduler) {
//		return new QuartzJobScheduler(scheduler);
//	}
//
//	@Autowired
//	@Bean
//	@Qualifier(QUARTZ)
//    Properties schedulerProperties(Environment env)
//    {
//        final Properties properties = new Properties();
//        final String name = QUARTZ + "-" + env.getActiveProfiles()[0] + ".properties"; // quartz-dev.properties
//        final InputStream resource = Thread.currentThread().getContextClassLoader()
//                                                     .getResourceAsStream(name);
//        try
//        {
//            properties.load(resource);
//            return properties;
//        }
//        catch (final IOException e)
//        {
//            throw new RuntimeException("Failed to load properties file", e);
//        }
//    }
//
//	@Autowired
//	@Bean
//    SchedulerFactory schedulerFactory(@Qualifier(QUARTZ) Properties properties)
//    {
//        final StdSchedulerFactory factory = new StdSchedulerFactory();
//        try
//        {
//            factory.initialize(properties);
//        }
//        catch (final SchedulerException e)
//        {
//            throw new RuntimeException("Failed to initialize scheduler factory", e);
//        }
//        return factory;
//    }
//
//	@Autowired
//	@Bean
//	Scheduler scheduler(SchedulerFactory schedulerFactory, JobFactory jobFactory)
//    {
//        try {
//            final Scheduler scheduler = schedulerFactory.getScheduler();
//            scheduler.setJobFactory(jobFactory);
//            return scheduler;
//        } catch (final SchedulerException e) {
//            final String error = "[ERROR] initialize quartz scheduler: " + e.getMessage();
//            throw new RuntimeException(error, e);
//        }
//    }

	@Autowired
	@Bean
	Job job(WorkflowLauncher launcher) {
		return new QuartzQueuedJob(launcher);
	}

}
