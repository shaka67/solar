/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver.db;

import java.util.List;

import com.ouer.solar.config.jobserver.JobServerSpecialConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface JobServerSpecialConfigMapper {

	public int insert(JobServerSpecialConfig config);

	public int update(JobServerSpecialConfig config);

	public JobServerSpecialConfig select(String appId);

	public List<JobServerSpecialConfig> selectAll();

	public boolean exists(String appId);

	public boolean delete(String appId);
}
