/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.mail.MailConfig;
import com.ouer.solar.config.security.Base64PasswordSecurity;
import com.ouer.solar.config.security.PasswordSecurity;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class MailConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MailConfigMapper mapper;

	@Test
	public void testInsert() throws Exception {
		final PasswordSecurity pwdSecurity = new Base64PasswordSecurity();
		MailConfig config = new MailConfig();
		config.setAppId("indra");
		config.setProtocol("smtps");
		config.setSmtpHost("smtp.exmail.qq.com");
		config.setSmtpPort(465);
		config.setSmtpProps("mail.smtps.host=smtp.exmail.qq.com,mail.debug=true,mail.smtps.auth=true,mail.smtps.socketFactory.class=javax.net.ssl.SSLSocketFactory,mail.smtps.socketFactory.fallback=false,mail.smtps.socketFactory.port=465,mail.smtp.ssl.enable=true");
		config.setUsername("indra@ixiaopu.com");
		config.setPassword(pwdSecurity.encrypt("xxxx"));
		config.setPersonal("【因陀罗】");
		mapper.insert(config);

		config = new MailConfig();
		config.setAppId("qianfan");
		config.setProtocol("smtps");
		config.setSmtpHost("smtp.exmail.qq.com");
		config.setSmtpPort(465);
		config.setSmtpProps("mail.smtps.host=smtp.exmail.qq.com,mail.debug=true,mail.smtps.auth=true,mail.smtps.socketFactory.class=javax.net.ssl.SSLSocketFactory,mail.smtps.socketFactory.fallback=false,mail.smtps.socketFactory.port=465,mail.smtp.ssl.enable=true");
		config.setUsername("no-replay@ixiaopu.com");
		config.setPassword(pwdSecurity.encrypt("yyyy"));
		config.setPersonal("【千帆】");
		mapper.insert(config);

		config = new MailConfig();
		config.setAppId("davebella");
		config.setProtocol("smtps");
		config.setSmtpHost("smtp.exmail.qq.com");
		config.setSmtpPort(465);
		config.setSmtpProps("mail.smtps.host=smtp.exmail.qq.com,mail.debug=true,mail.smtps.auth=true,mail.smtps.socketFactory.class=javax.net.ssl.SSLSocketFactory,mail.smtps.socketFactory.fallback=false,mail.smtps.socketFactory.port=465,mail.smtp.ssl.enable=true");
		config.setUsername("no-reply@davebella.com");
		config.setPassword("ZEIyMDE2YWRtc3U4OA==");
		config.setPersonal("【戴维贝拉】");
		mapper.insert(config);
	}

	@Test
	public void testSelect() {
		final MailConfig config = mapper.select("indra");
		System.err.println(config);
	}

	@Test
	public void testDelete() {
		mapper.delete("zzz");
	}
}
