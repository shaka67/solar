/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CommonConfig extends CommonConfigHeader {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// separated by separator like comma
	private String configOptions;

	public String[] getConfigOptionArray() {
		return StringUtil.split(configOptions, StringPool.Symbol.COMMA);
	}

	public String getConfigOptions() {
		return configOptions;
	}

	public void setConfigOptions(String configOptions) {
		this.configOptions = configOptions;
	}

	@Override
	public String toString() {
		return "CommonConfig [configOptions=" + configOptions + "]";
	}

}
