/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image.db;

import java.util.List;

import com.ouer.solar.config.image.ImageConfig;
import com.ouer.solar.config.image.ImageConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBImageConfigLocator implements ImageConfigLocator {

	private final ImageConfigMapper mapper;

	public DBImageConfigLocator(ImageConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public ImageConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<ImageConfig> getConfigs() {
		return mapper.selectAll();
	}

}
