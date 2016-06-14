/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.CommonConfigHeader;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface CommonConfigMapper {

	public int insert(CommonConfig config);

	public int update(CommonConfig config);

	public CommonConfig select(CommonConfigHeader header);

	public boolean exists(CommonConfigHeader config);
}
