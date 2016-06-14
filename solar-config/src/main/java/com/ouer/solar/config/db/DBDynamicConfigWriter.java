/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import com.ouer.solar.config.DynamicConfig;
import com.ouer.solar.config.DynamicConfigWriter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBDynamicConfigWriter implements DynamicConfigWriter {

	private final DynamicConfigMapper mapper;

	public DBDynamicConfigWriter(DynamicConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(DynamicConfig config) {
		if (mapper.exists(config)) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

}
