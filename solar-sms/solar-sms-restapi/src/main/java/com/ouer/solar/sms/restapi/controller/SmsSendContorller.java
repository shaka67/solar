/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.restapi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ouer.solar.sms.api.SmsApi;
import com.ouer.solar.sms.api.SmsMessage;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

@Controller
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
public class SmsSendContorller {

	private static final Logger LOG = LoggerFactory.getLogger(SmsSendContorller.class);

	@Autowired
	private SmsApi smsApi;

	@RequestMapping(value ="sms/send",method=RequestMethod.POST)
	public void send(String appId, int channel, String mobiles, String content,HttpServletResponse res){
		try {
			final SmsMessage message = new SmsMessage(appId);
			message.setChannel(channel);
			message.setMobile(mobiles);
			message.setContent(content);
			smsApi.send(message);
			res.getWriter().write("true");
		} catch (final IOException e) {
			LOG.error(e.getMessage(),e);
		}
	}



}
