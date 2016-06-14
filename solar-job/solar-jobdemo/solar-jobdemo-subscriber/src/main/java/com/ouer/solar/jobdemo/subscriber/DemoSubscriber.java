/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.jobdemo.subscriber;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerManager;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerDaemon;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DemoSubscriber {

	private static final Logger LOG = LoggerFactory.getLogger(DemoSubscriber.class);

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

		context.register(DemoSubscriberConfig.class);
		context.refresh();

		final RabbitMQWorkerServerManager manager = context.getBean(RabbitMQWorkerServerManager.class);

		final RabbitMQRemoteWorkerDaemon daemon1 = manager.getWorkerDaemon("indra");
		daemon1.start();
		final RabbitMQRemoteWorkerDaemon daemon2 = manager.getWorkerDaemon("qianfan");
		daemon2.start();
	}

}
