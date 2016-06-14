/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouer.solar.jmx.ClassLoadingStatisticer;
import com.ouer.solar.jmx.GCStatisticer;
import com.ouer.solar.jmx.JVMMemory;
import com.ouer.solar.jmx.OperatingSystem;
import com.ouer.solar.jmx.Runtime;
import com.ouer.solar.jmx.ThreadStatisticer;
import com.ouer.solar.jmx.TransactionStatisticer;
import com.ouer.solar.lang.AppInfo;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
//@ImportResource("classpath:/META-INF/applicationContext-jmx.xml")
public class JmxSpringConfig {

//	@Value("${mx4j.http.adaptor.port}")
//	int port;
//	@Value("${mx4j.http.adaptor.host}")
//	String host;
//	@Value("${mx4j.authentication.method}")
//	String method;
//
//	@Value("${mx4j.authentication.username}")
//	String username;
//	@Value("${login.password}")
//	String password;
//
//	@Bean
//	ProcessorMBean processorMBean() {
//		return new XSLTProcessor();
//	}
//
//	@Autowired
//	@Bean(name = "httpAdaptor")
//	MBeanHttpAdaptor mBeanHttpAdaptor(HttpAdaptor httpAdaptor) {
//		return new MBeanHttpAdaptor(httpAdaptor, username, password);
//	}
//
//	@Autowired
//	@Bean
//	HttpAdaptor HttpAdaptor(ProcessorMBean processor) {
//		final HttpAdaptor adaptor = new HttpAdaptor();
//		adaptor.setProcessor(processor);
//		adaptor.setHost(host);
//		adaptor.setPort(port);
//		adaptor.setAuthenticationMethod(method);
//		return adaptor;
//	}
//
//	@Bean(name = "mbeanServer")
//	MBeanServerFactoryBean mBeanServerFactoryBean() {
//		final MBeanServerFactoryBean server = new MBeanServerFactoryBean();
//        server.afterPropertiesSet();
//        return server;
//	}

	@Bean
	AppInfo appInfo() {
		return new AppInfo();
	}

	@Bean
	ClassLoadingStatisticer classLoadingStatisticer() {
		return new ClassLoadingStatisticer();
	}

	@Bean
	GCStatisticer gcStatisticer() {
		return new GCStatisticer();
	}

	@Bean
	JVMMemory jvmMemory() {
		return new JVMMemory();
	}

	@Bean
	OperatingSystem OS() {
		return new OperatingSystem();
	}

	@Bean
	Runtime runtime() {
		return new Runtime();
	}

	@Bean
	ThreadStatisticer threadStatisticer() {
		return new ThreadStatisticer();
	}

	@Bean
	TransactionStatisticer transactionStatisticer() {
		return new TransactionStatisticer();
	}
}
