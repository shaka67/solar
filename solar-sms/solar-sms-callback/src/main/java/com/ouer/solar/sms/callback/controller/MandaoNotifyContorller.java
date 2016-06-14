/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.callback.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ouer.solar.sms.api.SmsNotifyApi;
import com.ouer.solar.sms.thirdparty.mandao.MandaoConfigLocator;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */
//@Controller
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
public class MandaoNotifyContorller {

	private static final String NOTIFY_CLASS = "com.ouer.solar.sms.server.mandao.MandaoSmsNotifier";

	private static final Logger LOG = LoggerFactory.getLogger(MandaoNotifyContorller.class);

	private final SmsNotifyApi smsNotifyApi;
	private final MandaoConfigLocator locator;

	public MandaoNotifyContorller(SmsNotifyApi smsNotifyApi, MandaoConfigLocator locator) {
		this.smsNotifyApi = smsNotifyApi;
		this.locator = locator;
	}

	@RequestMapping("mandaoSms/notify")
 	public void smsNotify(HttpServletRequest req,HttpServletResponse res){
 		final String args = req.getParameter("args");
 		LOG.info("args: " + args);
 		if (StringUtils.isEmpty(args)) {
 			LOG.error("没有接收到参数");
 		} else {
 			final String appId = parseAppId(args);
 			smsNotifyApi.notified(appId, NOTIFY_CLASS, args);
 		}
 		try {
			res.getWriter().print("0");
		} catch (final IOException e) {
			LOG.error(e.getMessage());
		}
 	}

	public String parseAppId(String args) {
		if (StringUtils.isEmpty(args)) {
			return null;
		}
		final String firstBatch = args.split(";")[0];
		if (StringUtils.isEmpty(firstBatch)) {
			return null;
		}
		final String ssn = firstBatch.split(",")[1];
		return locator.locateAppId(ssn);
	}

}
