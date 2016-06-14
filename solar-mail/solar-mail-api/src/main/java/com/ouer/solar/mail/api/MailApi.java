/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.api;

import java.io.IOException;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MailApi
{
	public static final String CONFIG_TYPE = "mail";

	public void sendMessage(MailMessage message) throws MailException, IOException;
}
