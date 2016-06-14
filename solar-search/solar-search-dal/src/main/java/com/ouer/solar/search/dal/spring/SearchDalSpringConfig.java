/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.search.dal.dynamic.SearchDalManager;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ ConfigDBLocatorSpring.class, ConfigDalStoreSpring.class })
public class SearchDalSpringConfig {

	@Autowired
	@Bean
	SearchDalManager searchDalManager(SearchConfigLocator locator) {
		return new SearchDalManager(locator);
	}

//	@Value("classpath:config/MapperConfig.xml")
//	Resource mybatisMapperConfig;
//
//	@Autowired
//	DataSource dataSource;
//
//	@Bean
//	public IndexMapper indexMapper() throws Exception {
//		return newMapperFactoryBean(IndexMapper.class).getObject();
//	}
//
//	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz) throws Exception {
//		final MapperFactoryBean<T> b = new MapperFactoryBean<T>();
//		b.setMapperInterface(clazz);
//		b.setSqlSessionFactory(sqlSessionFactory());
//		return b;
//	}
//
//	@Bean
//	public SqlSessionFactory sqlSessionFactory() throws Exception {
//		final EsSqlSessionFactoryBean fb = new EsSqlSessionFactoryBean();
//		fb.setConfigLocation(mybatisMapperConfig);
//		fb.setDataSource(dataSource);
//
//		return fb.getObject();
//	}
}
