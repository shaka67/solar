/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.loadbalance.LoadBalancer;
import com.ouer.solar.loadbalance.RoundRobin;
import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.api.SmsResponse;
import com.ouer.solar.sms.api.SmsStatus;
import com.ouer.solar.sms.model.SendRecord;
import com.ouer.solar.sms.server.mapper.SmsMobileMapMapper;
import com.ouer.solar.sms.server.mapper.SmsRecordMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsServiceProxy {

	private static final Logger LOG = LoggerFactory.getLogger(SmsServiceProxy.class);

	private final LoadBalancer loadBalancer;

	private final List<SmsService> services;

	private final SmsRecordMapper smsRecordDalMapper;
	private final SmsMobileMapMapper smsMobileMapMapper;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	public SmsServiceProxy(List<SmsService> services,
							  SmsRecordMapper smsRecordDalMapper,
							  SmsMobileMapMapper smsMobileMapMapper) {
		loadBalancer = new RoundRobin();
		this.services = services;
		this.smsRecordDalMapper = smsRecordDalMapper;
		this.smsMobileMapMapper = smsMobileMapMapper;
	}

	public SmsServiceProxy(LoadBalancer loadBalancer,
							 List<SmsService> services,
							 SmsRecordMapper smsRecordDalMapper,
							 SmsMobileMapMapper smsMobileMapMapper) {
		this.loadBalancer = loadBalancer;
		this.services = services;
		this.smsRecordDalMapper = smsRecordDalMapper;
		this.smsMobileMapMapper = smsMobileMapMapper;
	}

	public SmsResponse<String> send(SmsMessage message) {
		SmsResponse<String> resp = new SmsResponse<String>();
		final int next = loadBalancer.next();
		final SmsService service = services.get(next);

		if (service instanceof LimitedSmsService) {
			if (((LimitedSmsService) service).getLimiter().tryAcquire()) {
				resp = service.send(message);
			}
			else {
				LOG.warn("sms service " + service.getClass().getName() + " exceeds rate limit "
						+ ((LimitedSmsService) service).getLimiter().getRate());
				// FIXME return what?
				resp.setStatus(SmsStatus.FAILURE);
				resp.setData("短信发送超过了指定频率，发送失败");
			}
		}
		else {
			resp = service.send(message);
		}

		//保存日志到数据库
		final SendRecord record = new SendRecord(message.getChannel(), message.getMobile(), message.getContent(),
				resp.getThirdBatchId(), resp.getThird(), String.valueOf(resp.getStatus().code()));

		executor.execute(new RecordSaver(record, smsMobileMapMapper, smsRecordDalMapper));

		return resp;
	}

}
