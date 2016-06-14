/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver.db;

import com.ouer.solar.config.jobserver.JobServerConfigAdmin;
import com.ouer.solar.config.jobserver.JobServerSpecialConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBJobServerConfigAdmin implements JobServerConfigAdmin {

	private final JobServerSpecialConfigMapper mapper;

	public DBJobServerConfigAdmin(JobServerSpecialConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(JobServerSpecialConfig config) {
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
