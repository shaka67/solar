/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientLifecycle;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientManager;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerClientConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerClientSyncConfiguration;
import com.ouer.solar.remote.RemoteStatus;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RabbitMQRemoteWorkerClientConfiguration.class,
									 RabbitMQRemoteWorkerClientSyncConfiguration.class,
									 DynamicRabbitMQConfiguration.class,
									 ConfigDBLocatorSpring.class,
									 ConfigDalStoreSpring.class,
		 							 ProfileSpringConfig.class })
public class RabbitMQRemoteWorkerClientTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQWorkerClientLifecycle lifecycle;
	@Autowired
	private RabbitMQWorkerClientManager manager;

	@Test
	public void testRemoteWorkerClient() throws Exception {
		lifecycle.start();
		final RabbitMQRemoteWorkerClient client1 = manager.getWorkerClient("indra");
		RemoteStatus status = client1.invoke("test1", "testRemoteMethod1",
				new Object[0]);
		System.out.println(status);
		status = client1.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);
		status = client1.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);
		status = client1.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);

		final RabbitMQRemoteWorkerClient client2 = manager.getWorkerClient("qianfan");
		status = client2.invoke("test1", "testRemoteMethod1",
				new Object[0]);
		System.out.println(status);
		status = client2.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);
		status = client2.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);
		status = client2.invoke("test1", "testRemoteMethod2",
				new Object[] { "parameter1" });
		System.out.println(status);

		synchronized (this) {
            while (true) {
                try {
                	wait();
                } catch (final Throwable e) {
                }
            }
        }
	}

}
