/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.sms.SmsConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.config.sync.ConfigSyncEvent;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.protocol.JsonObjectConverter;
import com.ouer.solar.sms.server.dynamic.SmsDalManager;
import com.ouer.solar.sms.server.dynamic.SmsLifecycle;
import com.ouer.solar.sms.server.dynamic.SmsServiceManager;
import com.ouer.solar.sms.server.sync.SmsSyncListener;
import com.ouer.solar.sms.thirdparty.ThirdpartySpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ThirdpartySpringConfig.class,
		    ConfigDBLocatorSpring.class,
		    ConfigDalStoreSpring.class,
		    ConfigSyncSpring.class})
@ImportResource({"classpath:/META-INF/applicationContext-sms-server.xml"})
public class SmsSpringConfig {

	@Value("classpath:config/MapperConfig.xml")
	Resource mybatisMapperConfig;

	@Autowired
	@Bean
	SmsDalManager smsDalManager(SmsConfigLocator locator) {
		return new SmsDalManager(locator, mybatisMapperConfig);
	}

	@Autowired
	@Bean
	SmsServiceManager smsServiceManager(SmsConfigLocator locator, SmsDalManager dalManager) {
		return new SmsServiceManager(locator, dalManager, new JsonObjectConverter());
	}

	@Autowired
	@Bean
	SmsLifecycle smsLifecycle(SmsConfigLocator locator,
			 					 ConfigSyncSubscriber subscriber,
			 					 SmsServiceManager manager) {
		return new SmsLifecycle(locator, subscriber, manager);
	}

	@Autowired
	@Bean
	SmsSyncListener smsSyncListener(BusSignalManager bsm, SmsLifecycle lifecycle) {
		final SmsSyncListener listener = new SmsSyncListener(lifecycle);
		bsm.bind(ConfigSyncEvent.class, listener);
		return listener;
	}

}
