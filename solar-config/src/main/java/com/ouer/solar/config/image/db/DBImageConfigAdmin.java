/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image.db;

import com.ouer.solar.config.image.ImageConfig;
import com.ouer.solar.config.image.ImageConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBImageConfigAdmin implements ImageConfigAdmin {

	private final ImageConfigMapper mapper;

	public DBImageConfigAdmin(ImageConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(ImageConfig config) {
		if (mapper.exists(config.getAppId())) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

	@Override
	public boolean remove(String appId) {
		return mapper.delete(appId);
	}

}
