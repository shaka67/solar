/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.api;

import java.io.Serializable;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsMessage implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String appId;
	private int channel;
	private String mobile;
	private String content;

	public SmsMessage(String appId) {
		this.appId = appId;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SmsMessage [appId=" + appId + ", channel=" + channel
				+ ", mobile=" + mobile + ", content=" + content + "]";
	}

}
