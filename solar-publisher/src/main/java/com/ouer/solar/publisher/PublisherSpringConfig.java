/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.publisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ouer.solar.config.spring.ConfigSyncSpring;
import com.ouer.solar.jmx.spring.JmxSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigSyncSpring.class, JmxSpringConfig.class })
@ImportResource("classpath:/META-INF/applicationContext-publisher.xml")
public class PublisherSpringConfig {

}
