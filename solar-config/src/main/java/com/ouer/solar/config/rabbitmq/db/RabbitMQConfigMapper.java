/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq.db;

import java.util.List;

import com.ouer.solar.config.rabbitmq.RabbitMQConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RabbitMQConfigMapper {

	public int insert(RabbitMQConfig config);

	public int update(RabbitMQConfig config);

	public RabbitMQConfig select(String appId);

	public boolean exists(String appId);

	public boolean delete(String appId);

	public List<RabbitMQConfig> selectAll();
}
