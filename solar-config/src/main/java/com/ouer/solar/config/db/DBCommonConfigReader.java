/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.CommonConfigHeader;
import com.ouer.solar.config.CommonConfigReader;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBCommonConfigReader implements CommonConfigReader {

	private final CommonConfigMapper mapper;

	public DBCommonConfigReader(CommonConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public CommonConfig read(CommonConfigHeader header) {
		return mapper.select(header);
	}

}
