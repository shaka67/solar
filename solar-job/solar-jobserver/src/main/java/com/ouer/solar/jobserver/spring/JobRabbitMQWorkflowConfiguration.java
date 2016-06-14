/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.jobserver.workflow.RabbitMQWorkflowLauncher;
import com.ouer.solar.jobserver.workflow.WorkflowLauncher;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQWorkerClientManager;
import com.ouer.solar.rabbitmq.dynamic.spring.DynamicRabbitMQConfiguration;
import com.ouer.solar.rabbitmq.dynamic.spring.RabbitMQRemoteWorkerClientConfiguration;
import com.ouer.solar.retry.RetryOperationTemplate;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ RabbitMQRemoteWorkerClientConfiguration.class,
			RetryOperationConfiguration.class,
			DynamicRabbitMQConfiguration.class })
public class JobRabbitMQWorkflowConfiguration {

	@Autowired
	@Bean
	WorkflowLauncher workflowLauncher(RabbitMQWorkerClientManager manager, RetryOperationTemplate retry) {
    	return new RabbitMQWorkflowLauncher(manager, retry);
    }

}
