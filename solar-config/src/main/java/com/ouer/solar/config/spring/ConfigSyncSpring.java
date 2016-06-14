/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.spring;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.bus.DefaultBusRegistry;
import com.ouer.solar.config.sync.ConfigSyncPublisher;
import com.ouer.solar.config.sync.ConfigSyncSubscriber;
import com.ouer.solar.rabbitmq.spring.NNDConfiguration;
import com.ouer.solar.rabbitmq.spring.RabbitMQConfiguration;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ RabbitMQConfiguration.class, NNDConfiguration.class })
public class ConfigSyncSpring {

	@Autowired
	@Bean
	ConfigSyncSubscriber configSyncSubscriber(CachingConnectionFactory factory,
			   										AmqpAdmin admin,
			   										BusSignalManager bsm) {
		return new ConfigSyncSubscriber(factory, admin, bsm);
	}

	@Autowired
	@Bean
	ConfigSyncPublisher configSyncPublisher(AmqpAdmin admin, RabbitTemplate template) {
		return new ConfigSyncPublisher(admin, template);
	}

	@Bean
	BusSignalManager busSignalManager() {
		return new BusSignalManager(new DefaultBusRegistry());
	}

}
