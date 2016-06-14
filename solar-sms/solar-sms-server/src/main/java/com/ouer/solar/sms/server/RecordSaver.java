/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import java.util.ArrayList;
import java.util.List;

import com.ouer.solar.sms.model.SendRecord;
import com.ouer.solar.sms.model.SmsMobileMap;
import com.ouer.solar.sms.model.SmsRecord;
import com.ouer.solar.sms.server.mapper.SmsMobileMapMapper;
import com.ouer.solar.sms.server.mapper.SmsRecordMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RecordSaver implements Runnable {

	private final SendRecord sendRecord;
	private final SmsMobileMapMapper smsMobileMapper;
	private final SmsRecordMapper smsRecordMapper;

	public RecordSaver(SendRecord sendRecord,
						 SmsMobileMapMapper smsMobileMapper,
						 SmsRecordMapper smsRecordMapper) {
		this.sendRecord = sendRecord;
		this.smsMobileMapper = smsMobileMapper;
		this.smsRecordMapper = smsRecordMapper;
	}

	@Override
	public void run() {
		final SmsRecord smsRecord = new SmsRecord();
		smsRecord.setChannelId(sendRecord.getChannelId());
		smsRecord.setContent(sendRecord.getContent());
		smsRecord.setThirdBatchId(sendRecord.getThirdBatchId());
		smsRecord.setThirdId(sendRecord.getThirdId());
		smsRecordMapper.save(smsRecord);
		final String[] mobiles =sendRecord.getMobile().split(",");
		final Long smsRecordId = smsRecord.getId();
		final String status = sendRecord.getStatus();
		final List<SmsMobileMap> smmList = new ArrayList<SmsMobileMap>(mobiles.length);
		for (final String m : mobiles) {
			final SmsMobileMap smm  = new SmsMobileMap();
			smm.setMobile(m);
			smm.setSmsSendRecordId(smsRecordId);
			smm.setStatus(status);
			smmList.add(smm);
		}
		smsMobileMapper.save(smmList);
	}

}
