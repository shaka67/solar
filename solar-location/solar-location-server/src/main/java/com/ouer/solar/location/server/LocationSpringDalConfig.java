/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.location.server;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@ImportResource({"classpath:/META-INF/applicationContext-location-dal.xml"})
public class LocationSpringDalConfig {

	@Value("classpath:config/MapperConfig.xml")
	Resource mybatisMapperConfig;

	@Autowired
	DataSource dataSource;

	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz) throws Exception {
		final MapperFactoryBean<T> b = new MapperFactoryBean<T>();
		b.setMapperInterface(clazz);
		b.setSqlSessionFactory(sqlSessionFactory());
		return b;
	}

	@Bean
    public ZoneMapper zoneMapper() throws Exception {
        return newMapperFactoryBean(ZoneMapper.class).getObject();
    }

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		final SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(mybatisMapperConfig);
		fb.setDataSource(dataSource);
		return fb.getObject();
	}
}
