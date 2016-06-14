/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail.db;

import com.ouer.solar.config.mail.MailConfig;
import com.ouer.solar.config.mail.MailConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBMailConfigAdmin implements MailConfigAdmin {

	private final MailConfigMapper mapper;

	public DBMailConfigAdmin(MailConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(MailConfig config) {
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
