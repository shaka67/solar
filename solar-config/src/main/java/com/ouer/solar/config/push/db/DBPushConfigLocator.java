/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push.db;

import java.util.List;

import com.ouer.solar.config.push.PushConfig;
import com.ouer.solar.config.push.PushConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBPushConfigLocator implements PushConfigLocator {

	private final PushConfigMapper mapper;

	public DBPushConfigLocator(PushConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public PushConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<PushConfig> getConfigs() {
		return mapper.selectAll();
	}

}
