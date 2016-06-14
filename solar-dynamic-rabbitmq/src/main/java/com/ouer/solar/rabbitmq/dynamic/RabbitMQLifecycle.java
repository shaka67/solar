/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import com.ouer.solar.annotation.JMX;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class RabbitMQLifecycle {

	private final RabbitMQManager manager;

	public RabbitMQLifecycle(RabbitMQManager manager) {
		this.manager = manager;
	}

	public void reload(String appId) throws Exception {
		synchronized (manager) {
			manager.removeAmqpAdmin(appId);
			manager.getAmqpAdmin(appId);
		}
	}

}
