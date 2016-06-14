/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push.db;

import com.ouer.solar.config.push.PushConfig;
import com.ouer.solar.config.push.PushConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBPushConfigAdmin implements PushConfigAdmin {

	private final PushConfigMapper mapper;

	public DBPushConfigAdmin(PushConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(PushConfig config) {
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
