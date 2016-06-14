/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RabbitMQConfigAdmin {

	public boolean write(RabbitMQConfig config);

	public boolean remove(String appId);
}
