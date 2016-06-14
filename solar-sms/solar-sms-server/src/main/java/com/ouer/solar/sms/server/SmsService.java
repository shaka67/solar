/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import com.ouer.solar.Initializable;
import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.api.SmsResponse;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SmsService extends Initializable {

	public SmsResponse<String> send(SmsMessage message);

	public double getRate();

	public void setRate(double rate);
}
