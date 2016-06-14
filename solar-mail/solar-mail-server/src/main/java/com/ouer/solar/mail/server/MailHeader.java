/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Mail necessary header including from, to, cc, bcc, replyTo and subject
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MailHeader {

	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String[] replyTo;
	private String subject;

	private Map<String, Object> mailAttrs = new HashMap<String, Object>();

	public String getFrom() {
		return from;
	}

	public MailHeader withFrom(String from) {
		this.from = from;
		return this;
	}

	public String[] getTo() {
		return to;
	}

	public MailHeader withTo(String[] to) {
		this.to = to;
		return this;
	}

	public String[] getCc() {
		return cc;
	}

	public MailHeader withCc(String[] cc) {
		this.cc = cc;
		return this;
	}

	public String[] getBcc() {
		return bcc;
	}

	public MailHeader withBcc(String[] bcc) {
		this.bcc = bcc;
		return this;
	}

	public String[] getReplyTo() {
		return replyTo;
	}

	public MailHeader withReplyTo(String[] replyTo) {
		this.replyTo = replyTo;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public MailHeader withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public Map<String, Object> getMailAttrs() {
		return mailAttrs;
	}

	public MailHeader withMailAttrs(Map<String, Object> mailAttrs) {
		this.mailAttrs = mailAttrs;
		return this;
	}

}
