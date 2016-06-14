/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push.db;

import java.util.List;

import com.ouer.solar.config.push.PushConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface PushConfigMapper {

	public int insert(PushConfig config);

	public int update(PushConfig config);

	public PushConfig select(String appId);

	public List<PushConfig> selectAll();

	public boolean exists(String appId);

	public boolean delete(String appId);
}
