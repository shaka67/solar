/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.jobdemo.producer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.jobclient.Job;
import com.ouer.solar.jobclient.JobSchedulerClient;
import com.ouer.solar.jobclient.JobStatus;
import com.ouer.solar.jobdemo.model.DemoInnerModel;
import com.ouer.solar.jobdemo.model.DemoModel;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DemoProducer {

	private static final Logger LOG = LoggerFactory.getLogger(DemoProducer.class);

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		final ConfigurableEnvironment environment = context
				.getEnvironment();
		try {
			environment.getPropertySources().addFirst(
					new ResourcePropertySource(
							"file:target/conf/filtered.properties"));
			LOG.info("filtered.properties loaded");
		} catch (final IOException e) {
			// it's ok if the file is not there. we will just log that info.
			LOG.info("didn't find filtered.properties in classpath so not loading it in the AppContextInitialized");
		}

		context.register(DemoProducerConfig.class);
		context.refresh();

		final DemoModel demo = createDemoModel();

        final JobSchedulerClient scheduler = context.getBean(JobSchedulerClient.class);

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, -9);
//
//        Job job = new Job("indra", "on.scheduled.model", "demo producer1").withGroup("jobdemo.workflow")
//				  .withArgument(new Object[] {demo})
//				  .withStartTime(calendar.getTime());
//        JobStatus status = scheduler.rescheduleJob(job);
//        LOG.info("scheduled " + job + " with a status " + status);
//
//        calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, -18);
//        job = new Job("indra", "on.scheduled.model", "demo producer2").withGroup("jobdemo.workflow")
//				  .withArgument(new Object[] {demo})
//				  .withStartTime(calendar.getTime());
//        status = scheduler.rescheduleJob(job);
//        LOG.info("scheduled " + job + " with a status " + status);
//
//        calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, -5);
//        job = new Job("indra", "on.scheduled.model", "demo producer3").withGroup("jobdemo.workflow")
//				  .withArgument(new Object[] {demo})
//				  .withStartTime(calendar.getTime());
//        status = scheduler.rescheduleJob(job);
//        LOG.info("scheduled " + job + " with a status " + status);
//
//        job = new Job("indra", "on.scheduled.model", "demo producer4").withGroup("jobdemo.workflow")
//				  .withArgument(new Object[] {demo});
//        status = scheduler.rescheduleJob(job);
//        LOG.info("scheduled " + job + " with a status " + status);

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
		Job job = new Job("indra", "on.scheduled.model", "demo producer").withGroup("jobdemo.workflow")
																  .withArgument(new Object[] {demo})
																  .withRepeatForever()
																  .withRepeatInterval(60, TimeUnit.SECONDS)
																  .withEndTime(calendar.getTime())
																  .withDurability(false);
//		scheduler.deleteJob(job);
		JobStatus status = scheduler.rescheduleJob(job);
        LOG.info("scheduled " + job + " with a status " + status);

//        job = new Job("on.scheduled.string.and.map", "demo producer").withGroup("jobdemo.workflow")
//				  .withArgument(new Object[] {33, "string1", createMap()})
//				  .withRepeatForever()
//				  .withRepeatInterval(60, TimeUnit.SECONDS)
//				  .withEndTime(calendar.getTime());
//		scheduler.deleteJob(job);
//		status = scheduler.scheduleJob(job);
//		LOG.info("scheduled " + job + " with a status " + status);

		job = new Job("qianfan", "on.scheduled.models", "demo producer").withGroup("jobdemo.workflow")
				  .withArgument(new Object[] {Lists.newArrayList(demo)})
				  .withRepeatForever()
				  .withRepeatInterval(60, TimeUnit.SECONDS)
				  .withEndTime(calendar.getTime())
				  .withDurability(false);
//		scheduler.deleteJob(job);
		status = scheduler.rescheduleJob(job);
		LOG.info("scheduled " + job + " with a status " + status);
	}

	private static DemoModel createDemoModel() {
		final DemoModel model = new DemoModel();
		model.setId(1234L);
		model.setName("shaka");
		model.setDate(new Date());
		final List<Integer> integers = Lists.newArrayList();
		integers.add(5);
		integers.add(6);
		integers.add(7);
		model.setIntegers(integers);
		final DemoInnerModel innerModel = new DemoInnerModel();
		final List<String> list = Lists.newArrayList();
		list.add("xxx");
		list.add("yyy");
		list.add("zzz");
		innerModel.setList(list);
		final Map<Integer, String> map = Maps.newHashMap();
		map.put(1, "aaa");
		map.put(2, "bbb");
		map.put(3, "ccc");
		innerModel.setMap(map);
		model.setInner(innerModel);
		return model;
	}

	private static Map<Integer, String> createMap() {
		final Map<Integer, String> map = Maps.newHashMap();
		map.put(1, "value1");
		map.put(2, "value2");
		map.put(3, "value3");
		return map;
	}
}
