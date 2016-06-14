/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SmsNotifier
{
	public boolean notified(String appId, Object args);

	public void initialize();
}
