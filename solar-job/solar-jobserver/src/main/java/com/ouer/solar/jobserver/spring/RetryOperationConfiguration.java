/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ouer.solar.retry.RetryOperationTemplate;
import com.ouer.solar.retry.RetryPolicy;
import com.ouer.solar.retry.SimpleRetryPolicy;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
public class RetryOperationConfiguration {

	@Bean
	@Scope("prototype")
    RetryPolicy retryPolicy()
    {
        final RetryPolicy policy = new SimpleRetryPolicy(3, 60 * 1000); // 3 times and 1 minutes interval
        return policy;
    }

	@Autowired
	@Bean
	@Scope("prototype")
    RetryOperationTemplate retryOperationTemplate(RetryPolicy policy)
    {
        final RetryOperationTemplate template = new RetryOperationTemplate(policy);
        return template;
    }
}
