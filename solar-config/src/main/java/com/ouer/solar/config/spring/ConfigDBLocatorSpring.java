/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.config.CommonConfigReader;
import com.ouer.solar.config.DynamicConfigLocator;
import com.ouer.solar.config.DynamicConfigReader;
import com.ouer.solar.config.db.CommonConfigMapper;
import com.ouer.solar.config.db.DBCommonConfigReader;
import com.ouer.solar.config.db.DBDynamicConfigReader;
import com.ouer.solar.config.db.DynamicConfigMapper;
import com.ouer.solar.config.image.ImageConfigLocator;
import com.ouer.solar.config.image.db.DBImageConfigLocator;
import com.ouer.solar.config.image.db.ImageConfigMapper;
import com.ouer.solar.config.jobserver.JobServerConfigLocator;
import com.ouer.solar.config.jobserver.db.DBJobServerConfigLocator;
import com.ouer.solar.config.jobserver.db.JobServerSpecialConfigMapper;
import com.ouer.solar.config.mail.MailConfigLocator;
import com.ouer.solar.config.mail.db.DBMailConfigLocator;
import com.ouer.solar.config.mail.db.MailConfigMapper;
import com.ouer.solar.config.mogilefs.MogilefsConfigLocator;
import com.ouer.solar.config.mogilefs.db.DBMogilefsConfigLocator;
import com.ouer.solar.config.mogilefs.db.MogilefsConfigMapper;
import com.ouer.solar.config.push.PushConfigLocator;
import com.ouer.solar.config.push.db.DBPushConfigLocator;
import com.ouer.solar.config.push.db.PushConfigMapper;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.config.rabbitmq.db.DBRabbitMQBaseConfigLocator;
import com.ouer.solar.config.rabbitmq.db.RabbitMQConfigMapper;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.search.db.DBSearchConfigLocator;
import com.ouer.solar.config.search.db.SearchConfigMapper;
import com.ouer.solar.config.sms.SmsConfigLocator;
import com.ouer.solar.config.sms.db.DBSmsConfigLocator;
import com.ouer.solar.config.sms.db.SmsConfigMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class ConfigDBLocatorSpring {

	@Autowired
	@Bean
	RabbitMQConfigLocator rabbitMQConfigLocator(RabbitMQConfigMapper mapper) {
		return new DBRabbitMQBaseConfigLocator(mapper);
	}

	@Autowired
	@Bean
	JobServerConfigLocator jobServerConfigLocator(JobServerSpecialConfigMapper mapper) {
		return new DBJobServerConfigLocator(mapper);
	}

	@Autowired
	@Bean
	MailConfigLocator mailConfigLocator(MailConfigMapper mapper) {
		return new DBMailConfigLocator(mapper);
	}

	@Autowired
	@Bean
	MogilefsConfigLocator mogilefsConfigLocator(MogilefsConfigMapper mapper) {
		return new DBMogilefsConfigLocator(mapper);
	}

	@Autowired
	@Bean
	SearchConfigLocator searchConfigLocator(SearchConfigMapper mapper) {
		return new DBSearchConfigLocator(mapper);
	}

	@Autowired
	@Bean
	SmsConfigLocator smsConfigLocator(SmsConfigMapper mapper) {
		return new DBSmsConfigLocator(mapper);
	}

	@Autowired
	@Bean
	ImageConfigLocator imageConfigLocator(ImageConfigMapper mapper) {
		return new DBImageConfigLocator(mapper);
	}

	@Autowired
	@Bean
	PushConfigLocator pushConfigLocator(PushConfigMapper mapper) {
		return new DBPushConfigLocator(mapper);
	}

	@Autowired
	@Bean
	CommonConfigReader CommonConfigReader(CommonConfigMapper mapper) {
		return new DBCommonConfigReader(mapper);
	}

	@Autowired
	@Bean
	DynamicConfigReader dynamicConfigReader(DynamicConfigMapper mapper) {
		return new DBDynamicConfigReader(mapper);
	}

	@Autowired
	@Bean
	DynamicConfigLocator dynamicConfigLocator(CommonConfigReader ccr, DynamicConfigReader dcr) {
		return new DynamicConfigLocator(ccr, dcr);
	}
}
