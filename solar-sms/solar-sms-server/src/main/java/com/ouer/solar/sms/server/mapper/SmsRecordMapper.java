package com.ouer.solar.sms.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.ouer.solar.sms.model.SmsRecord;

public interface SmsRecordMapper {

	int save(SmsRecord smsRecord);
	int update(SmsRecord smsRecord);
	SmsRecord selectByThirdBatchId(@Param("thirdBatchId") String thirdBatchId,@Param("thirdId") String thirdId);
}
