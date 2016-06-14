/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.api;

import java.io.Serializable;
import java.util.List;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MailMessage implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	private String to;
	private String subject;
	private String content;
//	private String fromPersonal;
	/**
	 * attachments is a url list which can only be used when server can access these urls
	 */
	private List<String> attachments;

	@Override
	public String getAppId() {
		return appId;
	}

	public MailMessage withAppId(String appId) {
		this.appId = appId;
		return this;
	}

	public String getTo() {
		return to;
	}

	public MailMessage withTo(String to) {
		this.to = to;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public MailMessage withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getContent() {
		return content;
	}

	public MailMessage withContent(String content) {
		this.content = content;
		return this;
	}

//	public String getFromPersonal() {
//		return fromPersonal;
//	}
//
//	public MailMessage withFromPersonal(String fromPersonal) {
//		this.fromPersonal = fromPersonal;
//		return this;
//	}

	public List<String> getAttachments() {
		return attachments;
	}

	public MailMessage withAttachments(List<String> attachments) {
		this.attachments = attachments;
		return this;
	}

}
