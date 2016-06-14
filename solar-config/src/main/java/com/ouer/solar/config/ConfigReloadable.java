/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ConfigReloadable {

	public void reload(String appId) throws Exception;
}
