/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Sets;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerServerConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerServerSyncConfiguration;
import com.ouer.solar.remote.RemoteClassDefinitions;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ RabbitMQRemoteWorkerServerConfiguration.class,
			RabbitMQRemoteWorkerServerSyncConfiguration.class,
	 		DynamicRabbitMQConfiguration.class,
	 		ConfigDBLocatorSpring.class,
			ConfigDalStoreSpring.class,
	 		ProfileSpringConfig.class })
public class RabbitMQRemoteWorkerTestConfiguration {

	@Bean(name = RabbitMQRemoteWorkerInterface.QUALIFIER_NAME)
	RemoteClassDefinitions remoteClassDefinitions() {
		final Set<Class<?>> remoteClasses = Sets.newHashSet();
		remoteClasses.add(RabbitMQRemoteWorkerTestImplementation1.class);
		remoteClasses.add(RabbitMQRemoteWorkerTestImplementation2.class);
		return new RemoteClassDefinitions(remoteClasses);
	}

	@Autowired
	@Bean
	RabbitMQRemoteWorkerTestImplementation1 workerTestImplementation1(RabbitMQRemoteServiceTestInterface1 interface1/*,
            RabbitMQRemoteServiceTestInterface2 interface2,
            RabbitMQRemoteServiceTestInterface3 interface3*/) {
		return new RabbitMQRemoteWorkerTestImplementation1(interface1/*, interface2, interface3*/);
	}

	@Autowired
	@Bean
	RabbitMQRemoteWorkerTestImplementation2 workerTestImplementation2(RabbitMQRemoteServiceTestInterface1 interface1/*,
            RabbitMQRemoteServiceTestInterface2 interface2,
            RabbitMQRemoteServiceTestInterface3 interface3*/) {
		return new RabbitMQRemoteWorkerTestImplementation2(interface1/*, interface2, interface3*/);
	}

	@Bean
    RabbitMQRemoteServiceTestInterface1 testInterface1() {
        return new RabbitMQRemoteServiceTestImplementation();
    }

//	@Bean
//    RabbitMQRemoteServiceTestInterface2 testInterface2() {
//    	return new RabbitMQRemoteServiceTestImplementation();
//    }
//
//	@Bean
//    RabbitMQRemoteServiceTestInterface3 testInterface3() {
//    	return new RabbitMQRemoteServiceTestImplementation();
//    }
}
