/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs.db;

import com.ouer.solar.config.mogilefs.MogilefsConfig;
import com.ouer.solar.config.mogilefs.MogilefsConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBMogilefsConfigAdmin implements MogilefsConfigAdmin {

	private final MogilefsConfigMapper mapper;

	public DBMogilefsConfigAdmin(MogilefsConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(MogilefsConfig config) {
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
