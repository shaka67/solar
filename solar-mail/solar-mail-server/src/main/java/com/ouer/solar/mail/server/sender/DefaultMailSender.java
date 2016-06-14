/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server.sender;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ouer.solar.config.mail.MailConfig;
import com.ouer.solar.config.mail.MailConfigLocator;
import com.ouer.solar.mail.api.MailException;
import com.ouer.solar.mail.server.MailHeader;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefaultMailSender implements MailSender {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultMailSender.class);

	private final MailConfigLocator locator;
	private final JavaMailSender jms;

	public DefaultMailSender(MailConfigLocator locator, JavaMailSender jms) {
		this.locator = locator;
		this.jms = jms;
	}

	@Override
	public void sendMessage(String appId, MailHeader header, String content)
			throws MailException, IOException {
		send(appId, header, content, null);
	}

	@Override
	public void sendMessage(String appId, MailHeader header, String content,
			List<File> attachments) throws MailException, IOException {
		send(appId, header, content, attachments);
	}

	protected void send(String appId, MailHeader header, String content, List<File> attachments) throws MailException, IOException {
		try {
			// because always load config every time, we do not need the sync subscriber.
			final MailConfig config = locator.getConfig(appId);
			if (config == null) {
				LOG.error("failed to locate mail config for {}", appId);
				return;
			}
			final MimeMessageHelper mmmHelper = new MimeMessageHelper(
					jms.createMimeMessage(), true, "UTF-8");

			if (StringUtils.isEmpty(header.getFrom())) {
				if (StringUtils.isEmpty(config.getPersonal())) {
					mmmHelper.setFrom(config.getUsername());
				} else {
					mmmHelper.setFrom(config.getUsername(), config.getPersonal());
				}
			} else {
				if (StringUtils.isEmpty(config.getPersonal())) {
					mmmHelper.setFrom(header.getFrom());
				} else {
					mmmHelper.setFrom(header.getFrom(), config.getPersonal());
				}
			}
			mmmHelper.setTo(header.getTo());
			mmmHelper.setSubject(header.getSubject());
			mmmHelper.setCc(header.getCc());
			mmmHelper.setBcc(header.getBcc());
			mmmHelper.setText(content, true);

			if (attachments != null) {
				for (final File file : attachments) {
					mmmHelper.addAttachment(file.getName(), file);
				}
			}
			if (jms instanceof ConfigableJavaMailSender) {
				((ConfigableJavaMailSender) jms).send(config, mmmHelper.getMimeMessage());
			} else {
				jms.send(mmmHelper.getMimeMessage());
			}
		} catch (final MessagingException e) {
			throw new MailException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new MailException(e);
		}
	}

}
