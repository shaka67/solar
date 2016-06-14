/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms.db;

import java.util.List;

import com.ouer.solar.config.sms.SmsConfig;
import com.ouer.solar.config.sms.SmsConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBSmsConfigLocator implements SmsConfigLocator {

	private final SmsConfigMapper mapper;

	public DBSmsConfigLocator(SmsConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public SmsConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<SmsConfig> getConfigs() {
		return mapper.selectAll();
	}

}
