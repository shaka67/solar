/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ContextConfiguration(classes = { SmsApiSpringConfig.class, ProfileSpringConfig.class })
public class SmsApiTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SmsApi smsApi;

	@Test
	public void testSms() {
		final SmsMessage message = new SmsMessage("c2c-gamma");
		message.setChannel(1);
		message.setMobile("13588489004");
		message.setContent("hhho");
		smsApi.send(message);
	}
}
