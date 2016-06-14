/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.spring;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQLifecycle;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQManager;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;
import com.ouer.solar.rabbitmq.spring.NNDConfiguration;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import(NNDConfiguration.class)
public class DynamicRabbitMQConfiguration {

//	@Autowired
//	@Bean
//    MessageConverter messageConverter(NoDirectDependentCarrierConverter nddCarrierConverter) {
//    	return new NDDHessianMessageConverter(nddCarrierConverter);
//    }
//
//	@Bean
//    NoDirectDependentCarrierConverter nddCarrierConverter() {
//    	return new JsonNDDCarrierConverter();
//    }

	@Autowired
	@Bean(name = "rabbitMQManager")
	RabbitMQManager rabbitMQManager(RabbitMQConfigLocator locator,
				 							MessageConverter converter,
				 							NoDirectDependentCarrierConverter nddCarrierConverter) {
		return new RabbitMQManager(locator, converter, nddCarrierConverter);
	}

	@Autowired
	@Bean
	RabbitMQLifecycle rabbitMQLifecycle(@Qualifier("rabbitMQManager") RabbitMQManager manager) {
		return new RabbitMQLifecycle(manager);
	}

}
