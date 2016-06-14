/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs.db;

import com.ouer.solar.config.mogilefs.MogilefsConfig;
import com.ouer.solar.config.mogilefs.MogilefsConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBMogilefsConfigLocator implements MogilefsConfigLocator {

	private final MogilefsConfigMapper mapper;

	public DBMogilefsConfigLocator(MogilefsConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public MogilefsConfig getConfig(String appId) {
		return mapper.select(appId);
	}

}
