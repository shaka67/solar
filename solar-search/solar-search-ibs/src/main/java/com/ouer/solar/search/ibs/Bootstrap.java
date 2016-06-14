/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.ouer.solar.search.ibs.config.IbsSpringConfig;
import com.ouer.solar.search.ibs.dynamic.IbsLifecycle;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Bootstrap {

	private final static Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

	public static void main(String[] args) throws Exception {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		final ConfigurableEnvironment environment = context
				.getEnvironment();
		try {
			environment.getPropertySources().addFirst(
					new ResourcePropertySource(
							"classpath:filtered.properties"));
			LOG.info("filtered.properties loaded");
		} catch (final IOException e) {
			// it's ok if the file is not there. we will just log that info.
			LOG.info("didn't find filtered.properties in classpath so not loading it in the AppContextInitialized");
		}

		context.register(IbsSpringConfig.class, ProfileSpringConfig.class);
		context.refresh();

		final IbsLifecycle lifecycle = context.getBean(IbsLifecycle.class);
		lifecycle.start();
	}
}
