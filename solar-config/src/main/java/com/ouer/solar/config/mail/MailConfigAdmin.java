/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MailConfigAdmin {

	public boolean write(MailConfig config);

	public boolean remove(String appId);
}
