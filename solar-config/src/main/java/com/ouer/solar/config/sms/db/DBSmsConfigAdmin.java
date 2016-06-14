/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms.db;

import com.ouer.solar.config.sms.SmsConfig;
import com.ouer.solar.config.sms.SmsConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBSmsConfigAdmin implements SmsConfigAdmin {

	private final SmsConfigMapper mapper;

	public DBSmsConfigAdmin(SmsConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(SmsConfig config) {
		if (mapper.exists(config.getAppId())) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

	@Override
	public boolean remove(String appId) {
		return mapper.delete(appId);
	}

}
