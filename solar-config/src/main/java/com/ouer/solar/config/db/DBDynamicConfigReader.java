/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import java.util.Map;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.DynamicConfig;
import com.ouer.solar.config.DynamicConfigReader;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBDynamicConfigReader implements DynamicConfigReader {

	private final DynamicConfigMapper mapper;

	public DBDynamicConfigReader(DynamicConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public DynamicConfig read(String appId, CommonConfig config) {
		final DynamicConfig result = new DynamicConfig(appId, config.getConfigType(), config.getThirdparty());
		final Map<String, Object> configs = mapper.select(appId, config);
		result.setConfigs(configs);
		return result;
	}

}
