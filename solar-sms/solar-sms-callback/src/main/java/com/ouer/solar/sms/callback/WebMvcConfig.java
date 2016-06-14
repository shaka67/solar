/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ouer.solar.sms.api.SmsNotifyApi;
import com.ouer.solar.sms.callback.controller.MandaoNotifyContorller;
import com.ouer.solar.sms.thirdparty.ThirdpartySpringConfig;
import com.ouer.solar.sms.thirdparty.mandao.MandaoConfigLocator;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ThirdpartySpringConfig.class, ProfileSpringConfig.class })
@ImportResource({ "classpath:META-INF/applicationContext-web.xml","classpath:META-INF/spring-security-context.xml" })
@ComponentScan(includeFilters = @Filter(Controller.class), useDefaultFilters = false)
class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Bean
	MandaoNotifyContorller mandaoNotifyContorller(SmsNotifyApi smsNotifyApi, MandaoConfigLocator locator) {
		return new MandaoNotifyContorller(smsNotifyApi, locator);
	}
}
