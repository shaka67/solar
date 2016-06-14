/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { JobSchedulerClientTestConfiguration.class })
public class JobSchedulerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private JobSchedulerClient client;

	@Test
	public void test() {
		final Job job = new Job("indra", "order.status.query", "orderId_218001")
		.withGroup("job.trade");
        final JobStatus status = client.deleteJob(job);
        System.out.println(status);
	}
}
