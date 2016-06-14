/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image.db;

import java.util.List;

import com.ouer.solar.config.image.ImageConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ImageConfigMapper {

	public int insert(ImageConfig config);

	public int update(ImageConfig config);

	public ImageConfig select(String appId);

	public List<ImageConfig> selectAll();

	public boolean exists(String appId);

	public boolean delete(String appId);
}
