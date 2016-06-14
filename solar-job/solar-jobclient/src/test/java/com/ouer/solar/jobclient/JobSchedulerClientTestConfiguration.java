/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.jobclient.spring.JobSchedulerClientConfiguration;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ JmxSpringConfig.class, JobSchedulerClientConfiguration.class, ProfileSpringConfig.class })
public class JobSchedulerClientTestConfiguration {

}
