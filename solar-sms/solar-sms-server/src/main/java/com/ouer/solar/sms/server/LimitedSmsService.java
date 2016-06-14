/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server;

import com.google.common.util.concurrent.RateLimiter;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class LimitedSmsService implements SmsService {

	private double rate;

	private RateLimiter limiter;

	@Override
	public double getRate() {
		return rate;
	}

	@Override
	public void setRate(double rate) {
		this.rate = rate;
		limiter = RateLimiter.create(rate);
	}

	public RateLimiter getLimiter() {
		return limiter;
	}

}
