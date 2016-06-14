/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SmsConfigAdmin {

	public boolean write(SmsConfig config);

	public boolean remove(String appId);
}
