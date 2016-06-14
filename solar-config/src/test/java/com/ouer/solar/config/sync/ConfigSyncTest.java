/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigSyncTestSpring.class, ProfileSpringConfig.class })
public class ConfigSyncTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ConfigSyncPublisher publisher;
	@Autowired
	private ConfigSyncSubscriber subscriber;
	@Autowired
	private DummyBsmListener listener;

	@Test
	public void testSync() {
		subscriber.bind("indra", "dummy");
		publisher.sync("indra", "dummy");

		final Object lock = new Object();
		synchronized (lock) {
            while (true) {
                try {
                	lock.wait();
                } catch (final Throwable e) {
                	return;
                }
            }
        }
	}

	@Test
	public void testSynced() {
		subscriber.bind("indra", "published test");

		final Object lock = new Object();
		synchronized (lock) {
            while (true) {
                try {
                	lock.wait();
                } catch (final Throwable e) {
                	return;
                }
            }
        }
	}
}
