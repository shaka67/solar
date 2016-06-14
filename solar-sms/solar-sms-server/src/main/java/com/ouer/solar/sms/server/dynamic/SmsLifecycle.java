/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.dynamic;

import com.ouer.solar.config.sms.SmsConfig;
import com.ouer.solar.config.sms.SmsConfigLocator;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.config.sync.ConfigSyncableLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsLifecycle extends ConfigSyncableLifecycle<SmsConfig> {

	private static final String CONFIG_TYPE = "sms";

	private final SmsServiceManager manager;

	public SmsLifecycle(SmsConfigLocator locator,
						 ConfigSyncSubscriber subscriber,
						 SmsServiceManager manager) {
		super(locator, subscriber);
		this.manager = manager;
	}

	@Override
	public String getConfigType() {
		return CONFIG_TYPE;
	}

	@Override
	protected void start(String appId) throws Exception {
		manager.getSmsProxy(appId);
	}

	@Override
	protected void stop(String appId) throws Exception {
		manager.removeSmsProxy(appId);
	}

}
