/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.CommonConfigWriter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBCommonConfigWriter implements CommonConfigWriter {

	private final CommonConfigMapper mapper;

	public DBCommonConfigWriter(CommonConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(CommonConfig config) {
		if (mapper.exists(config)) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

}
