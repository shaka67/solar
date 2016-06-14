/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.ouer.solar.jobserver.dynamic.JobServerLifecycle;
import com.ouer.solar.jobserver.spring.JobServerConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BootstrapTestCase {

	private static final Logger LOG = LoggerFactory.getLogger(BootstrapTestCase.class);

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
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

		context.register(JobServerConfiguration.class, ProfileSpringConfig.class);
		context.refresh();

		final JobServerLifecycle lifecycle = context.getBean(JobServerLifecycle.class);
		lifecycle.start();
		lifecycle.stop();
		lifecycle.start();
	}
}
