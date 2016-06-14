/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import java.util.List;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ConfigLocator<T extends BaseConfig> {

	public T getConfig(String appId);

	public List<T> getConfigs();
}
