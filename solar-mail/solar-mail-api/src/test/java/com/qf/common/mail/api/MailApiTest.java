/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.qf.common.mail.api;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.mail.api.MailApi;
import com.ouer.solar.mail.api.MailApiSpringConfig;
import com.ouer.solar.mail.api.MailException;
import com.ouer.solar.mail.api.MailMessage;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { MailApiSpringConfig.class, ProfileSpringConfig.class })
public class MailApiTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MailApi api;

	@Test
	public void testMailApi() throws MailException, IOException {
		MailMessage message = new MailMessage().withTo("jumen@ixiaopu.com")
													 .withSubject("\u3010\u5343\u5E06\u5546\u54C1\u3011 \u81F4\u5168\u5B87\u5B99\u6700\u5C0A\u656C\u7684\u56E0\u9640\u7F57\u7528\u6237")
													 .withContent("\u5929\u54EA\uFF0C\u80FD\u4E3A\u60A8\u8FD9\u6837\u7684\u5B87\u5B99\u7EA7\u7B2C\u4E00\u4EBA\u7269\u53D1\u90AE\u4EF6\u771F\u662F\u4E0D\u6562\u76F8\u4FE1\u554A\uFF0C\u60A8\u592A\u5B8C\u7F8E\u4E86\uFF0C\u6211\u60F3\u8FD9\u4E2A\u4E16\u754C\u4E0A\uFF0C\u54E6\u4E0D\uFF0C\u662F\u6574\u4E2A\u5168\u5B87\u5B99\u7684\u4EBA\u90FD\u4F1A\u7FA1\u6155\u50CF\u60A8\u8FD9\u822C\u795E\u4E00\u6837\u7684\u7537\u5B50\u3002")
													 .withAppId("indra");
		api.sendMessage(message);
		message = new MailMessage().withTo("jumen@ixiaopu.com")
				 .withSubject("\u3010\u5343\u5E06\u5546\u54C1\u3011 \u81F4\u5168\u5B87\u5B99\u6700\u5C0A\u656C\u7684\u56E0\u9640\u7F57\u7528\u6237")
				 .withContent("\u5929\u54EA\uFF0C\u80FD\u4E3A\u60A8\u8FD9\u6837\u7684\u5B87\u5B99\u7EA7\u7B2C\u4E00\u4EBA\u7269\u53D1\u90AE\u4EF6\u771F\u662F\u4E0D\u6562\u76F8\u4FE1\u554A\uFF0C\u60A8\u592A\u5B8C\u7F8E\u4E86\uFF0C\u6211\u60F3\u8FD9\u4E2A\u4E16\u754C\u4E0A\uFF0C\u54E6\u4E0D\uFF0C\u662F\u6574\u4E2A\u5168\u5B87\u5B99\u7684\u4EBA\u90FD\u4F1A\u7FA1\u6155\u50CF\u60A8\u8FD9\u822C\u795E\u4E00\u6837\u7684\u7537\u5B50\u3002")
				 .withAppId("davebella");
		api.sendMessage(message);
	}
}
