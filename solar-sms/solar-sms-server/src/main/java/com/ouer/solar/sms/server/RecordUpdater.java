/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import java.util.List;
import java.util.Map;

import com.ouer.solar.sms.model.SmsMobileMap;
import com.ouer.solar.sms.model.SmsRecord;
import com.ouer.solar.sms.server.mapper.SmsMobileMapMapper;
import com.ouer.solar.sms.server.mapper.SmsRecordMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RecordUpdater implements Runnable {

	private final Map<String,List<SmsMobileMap>> sendRecord;
	private final SmsMobileMapMapper smsMobileMapper;
	private final SmsRecordMapper smsRecordMapper;

	public RecordUpdater(Map<String, List<SmsMobileMap>> sendRecord,
						    SmsMobileMapMapper smsMobileMapper,
						    SmsRecordMapper smsRecordMapper) {
		this.sendRecord = sendRecord;
		this.smsMobileMapper = smsMobileMapper;
		this.smsRecordMapper = smsRecordMapper;
	}

	@Override
	public void run() {
		if (sendRecord != null) {
			for (final String batchId : sendRecord.keySet()) {
				final List<SmsMobileMap> smsMobileMapList = sendRecord.get(batchId);
				final SmsRecord smsRecord = smsRecordMapper.selectByThirdBatchId(batchId, "0");
				if (smsRecord != null) {
					final Long recordId = smsRecord.getId();
					for (final SmsMobileMap smm : smsMobileMapList) {
						smm.setSmsSendRecordId(recordId);
						smsMobileMapper.update(smm);
					}
				}
				smsRecordMapper.update(smsRecord);
			}
		}
	}

}
