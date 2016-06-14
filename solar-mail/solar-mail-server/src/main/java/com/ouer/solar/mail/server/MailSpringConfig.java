/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mail.server;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.javamail.JavaMailSender;

import com.ouer.solar.StringPool;
import com.ouer.solar.config.mail.MailConfigLocator;
import com.ouer.solar.config.security.Base64PasswordSecurity;
import com.ouer.solar.config.security.PasswordSecurity;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.mail.server.sender.ConfigableJavaMailSender;
import com.ouer.solar.mail.server.sender.DefaultMailSender;
import com.ouer.solar.mail.server.sender.MailSender;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ConfigDBLocatorSpring.class, ConfigDalStoreSpring.class})
@ImportResource({"classpath:/META-INF/applicationContext-mail-server.xml"})
public class MailSpringConfig {

	@Value("${mail.bcc}")
	private String mailBccs;

	@Autowired
	@Bean
	JavaMailSender JavaMailSender(PasswordSecurity passwordSecurity) {
		return new ConfigableJavaMailSender(passwordSecurity);
	}

	@Autowired
	@Bean
	MailSender mailSender(MailConfigLocator locator, JavaMailSender jms) {
		return new DefaultMailSender(locator, jms);
	}

	@Autowired
	@Bean
	MailService mailService(MailSender sender) {
		final String[] bcc = StringUtils.split(mailBccs, StringPool.Symbol.SEMICOLON);
		return new MailService(sender, bcc);
	}

	@Bean
	PasswordSecurity passwordSecurity() {
		return new Base64PasswordSecurity();
	}

}
