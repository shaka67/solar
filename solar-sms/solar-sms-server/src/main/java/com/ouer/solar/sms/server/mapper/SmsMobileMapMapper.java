/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.server.mapper;

import java.util.List;

import com.ouer.solar.sms.model.SmsMobileMap;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public interface SmsMobileMapMapper {
	int save(List<SmsMobileMap> smsRecord);
	int update(SmsMobileMap smsRecord);
	List<SmsMobileMap> selectByRecordId(Long smsRecordId);
}
