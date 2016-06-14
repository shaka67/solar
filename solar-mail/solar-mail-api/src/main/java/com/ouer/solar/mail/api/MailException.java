/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.api;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailException() {
		super();
	}

	public MailException(String msg) {
		super(msg);
	}

	public MailException(Throwable t) {
		super(t);
	}

	public MailException(String msg, Throwable t) {
		super(msg, t);
	}

}
