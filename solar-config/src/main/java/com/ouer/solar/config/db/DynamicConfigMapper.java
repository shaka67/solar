/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.db;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ouer.solar.config.CommonConfig;
import com.ouer.solar.config.DynamicConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DynamicConfigMapper {

	public int insert(DynamicConfig config);

	public int update(DynamicConfig config);

	public Map<String, Object> select(@Param("appId") String appId, @Param("config") CommonConfig config);

//	public List<Map<String, Object>> selectAll(@Param("config") CommonConfig config);

	public boolean exists(DynamicConfig config);
}
