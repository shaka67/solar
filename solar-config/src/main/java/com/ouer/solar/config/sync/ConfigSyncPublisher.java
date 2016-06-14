/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.ouer.solar.StringPool;
import com.ouer.solar.annotation.JMX;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class ConfigSyncPublisher implements ConfigSyncInterface {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigSyncPublisher.class);

	private final AmqpAdmin admin;
	private final RabbitTemplate template;

	public ConfigSyncPublisher(AmqpAdmin admin, RabbitTemplate template) {
		this.admin = admin;
		this.template = template;
	}

	public void sync(String appId, String configType) {
		final Exchange exchange = new TopicExchange(DEFAULT_CONFIG_EXCHANGE_NAME, true, false);
        admin.declareExchange(exchange);
        final String key = appId + StringPool.Symbol.COLON + configType;
        template.convertAndSend(DEFAULT_CONFIG_EXCHANGE_NAME, key,
        		new Message(key.getBytes(), new MessageProperties()));
        if (LOG.isInfoEnabled()) {
			LOG.info("publish a sync message {}", key);
		}
	}

}
