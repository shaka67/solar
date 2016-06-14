/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.spi;

import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.api.SmsResponse;
import com.ouer.solar.sms.server.SmsService;

/**
 *
 * @author: chenxi
 */

public class MockSmsService implements SmsService {

	@Override
	public SmsResponse<String> send(SmsMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRate(double rate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

}
