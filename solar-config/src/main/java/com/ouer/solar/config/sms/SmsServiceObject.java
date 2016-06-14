/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sms;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsServiceObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String smsServiceClass;
	private double rate;
	private String smsNotifierClass;

	public String getSmsServiceClass() {
		return smsServiceClass;
	}

	public void setSmsServiceClass(String smsServiceClass) {
		this.smsServiceClass = smsServiceClass;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getSmsNotifierClass() {
		return smsNotifierClass;
	}

	public void setSmsNotifierClass(String smsNotifierClass) {
		this.smsNotifierClass = smsNotifierClass;
	}

}
