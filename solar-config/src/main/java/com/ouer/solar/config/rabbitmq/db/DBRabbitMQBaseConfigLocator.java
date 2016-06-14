/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq.db;

import java.util.List;

import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBRabbitMQBaseConfigLocator implements RabbitMQConfigLocator {

	private final RabbitMQConfigMapper mapper;

	public DBRabbitMQBaseConfigLocator(RabbitMQConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public RabbitMQConfig getConfig(String appId) {
		return mapper.select(appId);
	}

	@Override
	public List<RabbitMQConfig> getConfigs() {
		return mapper.selectAll();
	}
}
