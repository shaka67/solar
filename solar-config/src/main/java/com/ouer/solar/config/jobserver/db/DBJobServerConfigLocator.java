/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver.db;

import java.util.List;

import com.ouer.solar.config.jobserver.JobServerConfigLocator;
import com.ouer.solar.config.jobserver.JobServerSpecialConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBJobServerConfigLocator implements JobServerConfigLocator {

	private final JobServerSpecialConfigMapper mapper;

	public DBJobServerConfigLocator(JobServerSpecialConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public JobServerSpecialConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<JobServerSpecialConfig> getConfigs() {
		return mapper.selectAll();
	}

}
