/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DynamicConfigLocator {

	protected final CommonConfigReader ccr;
	protected final DynamicConfigReader dcr;

	public DynamicConfigLocator(CommonConfigReader ccr,
						  		   DynamicConfigReader dcr) {
		this.ccr = ccr;
		this.dcr = dcr;
	}

	public CommonConfig loadCommonConfig(CommonConfigHeader header) {
		return ccr.read(header);
	}

	public DynamicConfig loadDynamicConfig(String appId, CommonConfigHeader header) {
		final CommonConfig common = ccr.read(header);
		return dcr.read(appId, common);
	}

}
