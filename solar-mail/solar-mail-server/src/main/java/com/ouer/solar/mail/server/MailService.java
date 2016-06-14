/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.ouer.solar.mail.api.MailApi;
import com.ouer.solar.mail.api.MailException;
import com.ouer.solar.mail.api.MailMessage;
import com.ouer.solar.mail.server.sender.MailSender;

/**
 * The dubbox exposed service which {@link MailApi} communicates with.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MailService implements MailApi {

	private final MailSender sender;
	/**
	 * the default bcc mail, it should be one ore some admins like role.
	 */
	private final String[] bcc;

	public MailService(MailSender sender, String[] bcc) {
		this.sender = sender;
		this.bcc = bcc;
	}

	@Override
	public void sendMessage(MailMessage message) throws MailException,
			IOException {
		final MailHeader header = new MailHeader().withTo(new String[] {message.getTo()})
				.withCc(new String[] {})
				.withBcc(bcc)
				.withSubject(message.getSubject());
		final List<String> urls = message.getAttachments();
		if (urls != null && !urls.isEmpty()) {
			final List<File> attachments = Lists.newArrayList();
			for (final String url : urls) {
				attachments.add(new File(url));
			}
			sender.sendMessage(message.getAppId(), header, message.getContent(), attachments);
		} else {
			sender.sendMessage(message.getAppId(), header, message.getContent());
		}
	}

}
