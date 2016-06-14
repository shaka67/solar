/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server.sender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ouer.solar.mail.api.MailException;
import com.ouer.solar.mail.server.MailHeader;

/**
 * An internal mail sender interface won't exposed to external application
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MailSender
{
	/**
	 * simply send a mail with a string content
	 * @throws IOException
	 * @throws MailFromException
	 */
	public void sendMessage(String appId, MailHeader header, String content) throws MailException, IOException;

	/**
	 * simply send a mail with a string content and a list of attachments
	 * @throws IOException
	 * @throws MailFromException
	 */
	public void sendMessage(String appId, MailHeader header, String content, List<File> attachments) throws MailException, IOException;

}
