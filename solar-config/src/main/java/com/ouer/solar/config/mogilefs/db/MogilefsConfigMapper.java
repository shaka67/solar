/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs.db;

import com.ouer.solar.config.mogilefs.MogilefsConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MogilefsConfigMapper {

	public int insert(MogilefsConfig config);

	public int update(MogilefsConfig config);

	public MogilefsConfig select(String appId);

	public boolean exists(String appId);

	public boolean delete(String appId);
}
