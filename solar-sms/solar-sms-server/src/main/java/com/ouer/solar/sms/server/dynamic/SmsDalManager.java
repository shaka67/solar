/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.dynamic;

import org.springframework.core.io.Resource;

import com.ouer.solar.config.DataSourceManager;
import com.ouer.solar.config.sms.SmsConfig;
import com.ouer.solar.config.sms.SmsConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsDalManager extends DataSourceManager<SmsConfig> {

	private final Resource dalConfigFile;

	public SmsDalManager(SmsConfigLocator locator, Resource dalConfigFile) {
		super(locator);
		this.dalConfigFile = dalConfigFile;
	}

	@Override
	protected Resource getConfigFile(SmsConfig config) throws Exception {
		return dalConfigFile;
	}

}
