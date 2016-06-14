/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.restapi;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {ProfileSpringConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected void customizeRegistration(
			ServletRegistration.Dynamic registration) {
		registration.setInitParameter("defaultHtmlEscape", "true");
	}
	@Override
	protected Filter[] getServletFilters() {
		final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("utf-8");
		characterEncodingFilter.setForceEncoding(true);

		return new Filter[] {characterEncodingFilter};
	}

	static class ConfigApplicationContextInitializer implements
			ApplicationContextInitializer<ConfigurableApplicationContext> {

		private final Logger LOG = LoggerFactory
				.getLogger(ConfigApplicationContextInitializer.class);

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			final ConfigurableEnvironment environment = applicationContext
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
		}
	}
}