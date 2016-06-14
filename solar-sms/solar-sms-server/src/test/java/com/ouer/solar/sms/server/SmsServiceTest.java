/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.sms.api.SmsApiSpringConfig;
import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.server.dynamic.SmsServiceManager;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: chenxi
 */
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ProfileSpringConfig.class, SmsApiSpringConfig.class })
public class SmsServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SmsServiceManager manager;

	@Test
	public void testSms() {
		final SmsMessage message = new SmsMessage("indra");
		message.setChannel(1);
		message.setMobile("13588489004");
		message.setContent("hhho");
		manager.send(message);
	}

//	@Test
//	public void testSmsNotify(){
//		final String args = "123456,62891,13675892404,564687,DELIVRD,2009-10-19 13:01:36;123456,62891,13675892403,564687,DELIVRD,2009-10-19 13:01:36;1127865,62891,13475892403,420937,DELIVRD,2009-10-19 13:01:42";
//		smsNotifyService.smsNotify(args);
//		synchronized (this) {
//            while (true) {
//                try {
//                	wait();
//                } catch (final Throwable e) {
//                }
//            }
//        }
//	}

}
