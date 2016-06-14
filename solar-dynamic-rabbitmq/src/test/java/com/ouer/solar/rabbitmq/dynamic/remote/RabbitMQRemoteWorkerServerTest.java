/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerServerLifecycle;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RabbitMQRemoteWorkerTestConfiguration.class })
public class RabbitMQRemoteWorkerServerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitMQWorkerServerLifecycle lifecycle;
//	@Autowired
//	private RabbitMQWorkerServerManager manager;

	@Test
	public void testRemoteWorkerServer() throws Exception {
		lifecycle.start();
//		final RabbitMQRemoteWorkerDaemon daemon1 = manager.getWorkerDaemon("indra");
//		daemon1.start();
//		final RabbitMQRemoteWorkerDaemon daemon2 = manager.getWorkerDaemon("qianfan");
//		daemon2.start();
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
