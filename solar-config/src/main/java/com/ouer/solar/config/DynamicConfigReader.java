/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DynamicConfigReader
{
	public DynamicConfig read(String appId, CommonConfig config);
}
