/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq.db;

import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigAdmin;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DBRabbitMQConfigAdmin implements RabbitMQConfigAdmin {

	private final RabbitMQConfigMapper mapper;

	public DBRabbitMQConfigAdmin(RabbitMQConfigMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean write(RabbitMQConfig config) {
		if (mapper.exists(config.getAppId())) {
			return mapper.update(config) == 1;
		}

		return mapper.insert(config) == 1;
	}

	@Override
	public boolean remove(String appId) {
		return mapper.delete(appId);
	}

}
