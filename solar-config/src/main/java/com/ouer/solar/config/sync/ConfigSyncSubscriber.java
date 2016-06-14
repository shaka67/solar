/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.google.common.collect.Maps;
import com.ouer.solar.StringPool;
import com.ouer.solar.bus.BusSignalManager;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ConfigSyncSubscriber implements ConfigSyncInterface {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigSyncSubscriber.class);

	private final CachingConnectionFactory factory;
	private final AmqpAdmin admin;
	private final BusSignalManager bsm;

	private final ConcurrentMap<String, SimpleMessageListenerContainer> containers = Maps.newConcurrentMap();

	public ConfigSyncSubscriber(CachingConnectionFactory factory,
								   AmqpAdmin admin,
								   BusSignalManager bsm) {
		this.factory = factory;
		this.admin = admin;
		this.bsm = bsm;
	}

	public void bind(String appId, String configType) {
		if (containers.containsKey(configType)) {
			LOG.warn("already bind {} to exchange", configType);
			return;
		}
		final TopicExchange exchange = new TopicExchange(DEFAULT_CONFIG_EXCHANGE_NAME, true, false);
        admin.declareExchange(exchange);
        final Queue queue = admin.declareQueue();
        final String key = appId + StringPool.Symbol.COLON + configType;
        final Binding binding = BindingBuilder.bind(queue).to(exchange).with(key);
        admin.declareBinding(binding);

        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setQueues(queue);
        // FIXME use singleton listener instance or prototype listener instance? -- now use the former.
        container.setMessageListener(new ConfigSyncMessageListener(key, bsm));
        if (containers.putIfAbsent(key, container) == null) {
			container.start();
			if (LOG.isInfoEnabled()) {
				LOG.info("binded exchange {} to routingKey {}", DEFAULT_CONFIG_EXCHANGE_NAME, key);
			}
		}
	}

}
