/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail.db;

import com.ouer.solar.config.mail.MailConfig;
import com.ouer.solar.config.mail.MailConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBMailConfigLocator implements MailConfigLocator {

	private final MailConfigMapper mapper;

	public DBMailConfigLocator(MailConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public MailConfig getConfig(String appId) {
		return mapper.select(appId);
	}

}
