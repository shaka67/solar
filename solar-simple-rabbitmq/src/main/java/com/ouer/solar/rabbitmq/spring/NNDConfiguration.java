/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.spring;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.rabbitmq.NDDHessianMessageConverter;
import com.ouer.solar.rabbitmq.nnd.JsonNDDCarrierConverter;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class NNDConfiguration {

	@Autowired
	@Bean
    MessageConverter messageConverter(NoDirectDependentCarrierConverter nddCarrierConverter) {
    	return new NDDHessianMessageConverter(nddCarrierConverter);
    }

	@Bean
    NoDirectDependentCarrierConverter nddCarrierConverter() {
    	return new JsonNDDCarrierConverter();
    }

}
