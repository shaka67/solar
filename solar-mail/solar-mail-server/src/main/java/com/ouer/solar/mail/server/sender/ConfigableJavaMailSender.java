/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server.sender;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.collect.Maps;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.config.mail.MailConfig;
import com.ouer.solar.config.security.PasswordSecurity;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ConfigableJavaMailSender extends JavaMailSenderImpl {

	private static final String HEADER_MESSAGE_ID = "Message-ID";

	private static final Logger LOG = LoggerFactory.getLogger(ConfigableJavaMailSender.class);

//	private final MailConfigClient configClient;
	private final PasswordSecurity passwordSecurity;
	private final Map<String, Session> sessions = Maps.newHashMap();

	public ConfigableJavaMailSender(PasswordSecurity passwordSecurity) {
//		this.configClient = configClient;
		this.passwordSecurity = passwordSecurity;
	}

	public void send(MailConfig config, MimeMessage mimeMessage) throws MailException {
		send(config, new MimeMessage[] {mimeMessage});
	}

	public void send(MailConfig config, MimeMessage[] mimeMessages) throws MailException {
		doSend(config, mimeMessages, null);
	}

	protected void doSend(MailConfig config, MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
		final Map<Object, Exception> failedMessages = new LinkedHashMap<Object, Exception>();

		Transport transport;
		try {
			final String username = config.getUsername();
			if (username == null) {
				throw new MailSendException("Invalid username of sender [" + username + "]");
			}
			final String password = passwordSecurity.decrypt(config.getPassword());
			transport = getTransport(config, getSession(config));
			transport.connect(config.getSmtpHost(), config.getSmtpPort(), username, password);
		}
		catch (final AuthenticationFailedException ex) {
			throw new MailAuthenticationException(ex);
		}
		catch (final MessagingException ex) {
			// Effectively, all messages failed...
			for (int i = 0; i < mimeMessages.length; i++) {
				final Object original = (originalMessages != null ? originalMessages[i] : mimeMessages[i]);
				failedMessages.put(original, ex);
			}
			throw new MailSendException("Mail server connection failed", ex, failedMessages);
		}
		catch (final Exception ex) {
			throw new MailAuthenticationException(ex);
		}

		try {
			for (int i = 0; i < mimeMessages.length; i++) {
				final MimeMessage mimeMessage = mimeMessages[i];
				try {
					if (mimeMessage.getSentDate() == null) {
						mimeMessage.setSentDate(new Date());
					}
					final String messageId = mimeMessage.getMessageID();
					mimeMessage.saveChanges();
					if (messageId != null) {
						// Preserve explicitly specified message id...
						mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
					}
					transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				}
				catch (final MessagingException ex) {
					final Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
					failedMessages.put(original, ex);
				}
			}
		}
		finally {
			try {
				transport.close();
			}
			catch (final MessagingException ex) {
				if (!failedMessages.isEmpty()) {
					throw new MailSendException("Failed to close server connection after message failures", ex,
							failedMessages);
				}
				else {
					throw new MailSendException("Failed to close server connection after message sending", ex);
				}
			}
		}

		if (!failedMessages.isEmpty()) {
			throw new MailSendException(failedMessages);
		}
	}

	protected Transport getTransport(MailConfig config, Session session) throws NoSuchProviderException {
		String protocol = config.getProtocol();
		if (protocol == null) {
			protocol = session.getProperty("mail.transport.protocol");
			if (protocol == null) {
				protocol = DEFAULT_PROTOCOL;
			}
		}
		return session.getTransport(protocol);
	}

//	private String getEmailFrom(MimeMessage message) throws MessagingException {
//		final Address[] addresses = message.getFrom();
//		if (addresses == null || addresses.length == 0) {
//			throw new MessagingException("empty from address in message [" + message + "]");
//		}
//
//		// choose the first one
//		final Address address = addresses[0];
//		if (address instanceof InternetAddress) {
//			return ((InternetAddress) address).getAddress();
//		}
//		return address.toString();
//	}

	public Session getSession(MailConfig config) {
		Session session = sessions.get(config.getAppId());
		if (session == null) {
			final Properties props = getProperties(config);
			session = Session.getInstance(props);
			sessions.put(config.getAppId(), session);
		}

		return session;
	}

	public Properties getProperties(MailConfig config) {
		if (config == null) {
			LOG.warn("no mail config found");
			return null;
		}

		final String mailProps = config.getSmtpProps();
		final Properties props = new Properties();
		final String[] pairs = StringUtil.split(mailProps, StringPool.Symbol.COMMA);
		String key;
		String value;
		for (final String pair : pairs) {
			key = StringUtil.split(pair, StringPool.Symbol.EQUALS)[0];
			value = StringUtil.split(pair, StringPool.Symbol.EQUALS)[1];
			props.put(key, value);
		}

		return props;
	}
}
