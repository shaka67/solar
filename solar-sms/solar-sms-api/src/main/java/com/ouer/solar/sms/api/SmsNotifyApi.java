/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.api;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public interface SmsNotifyApi {

	public boolean notified(String appId, String notifyClass, Object args);
}
