/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.spring;

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

import com.ouer.solar.config.db.CommonConfigMapper;
import com.ouer.solar.config.db.DynamicConfigMapper;
import com.ouer.solar.config.image.db.ImageConfigMapper;
import com.ouer.solar.config.jobserver.db.JobServerSpecialConfigMapper;
import com.ouer.solar.config.mail.db.MailConfigMapper;
import com.ouer.solar.config.mogilefs.db.MogilefsConfigMapper;
import com.ouer.solar.config.push.db.PushConfigMapper;
import com.ouer.solar.config.rabbitmq.db.RabbitMQConfigMapper;
import com.ouer.solar.config.search.db.SearchConfigMapper;
import com.ouer.solar.config.sms.db.SmsConfigMapper;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@ImportResource("classpath:/META-INF/applicationContext-config-dal.xml")
public class ConfigDalStoreSpring {

	@Value("classpath:config/solar-config-MapperConfig.xml")
	Resource mybatisMapperConfig;

	@Autowired
	DataSource solarConfigDataSource;

	@Bean
	RabbitMQConfigMapper rabbitMQMapper() throws Exception {
		return newMapperFactoryBean(RabbitMQConfigMapper.class).getObject();
	}

	@Bean
	JobServerSpecialConfigMapper jobServerSpecialConfigMapper() throws Exception {
		return newMapperFactoryBean(JobServerSpecialConfigMapper.class).getObject();
	}

	@Bean
	MailConfigMapper mailConfigMapper() throws Exception {
		return newMapperFactoryBean(MailConfigMapper.class).getObject();
	}

	@Bean
	MogilefsConfigMapper mogilefsConfigMapper() throws Exception {
		return newMapperFactoryBean(MogilefsConfigMapper.class).getObject();
	}

	@Bean
	SearchConfigMapper searchConfigMapper() throws Exception {
		return newMapperFactoryBean(SearchConfigMapper.class).getObject();
	}

	@Bean
	SmsConfigMapper smsConfigMapper() throws Exception {
		return newMapperFactoryBean(SmsConfigMapper.class).getObject();
	}

	@Bean
	ImageConfigMapper imageConfigMapper() throws Exception {
		return newMapperFactoryBean(ImageConfigMapper.class).getObject();
	}

	@Bean
	PushConfigMapper pushConfigMapper() throws Exception {
		return newMapperFactoryBean(PushConfigMapper.class).getObject();
	}

	@Bean
	CommonConfigMapper commonConfigMapper() throws Exception {
		return newMapperFactoryBean(CommonConfigMapper.class).getObject();
	}

	@Bean
	DynamicConfigMapper dynamicConfigMapper() throws Exception {
		return newMapperFactoryBean(DynamicConfigMapper.class).getObject();
	}

	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz) throws Exception {
		final MapperFactoryBean<T> b = new MapperFactoryBean<T>();
		b.setMapperInterface(clazz);
		b.setSqlSessionFactory(sqlSessionFactory());
		return b;
	}

	@Bean(name = "solarConfigSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory() throws Exception {
		final SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(mybatisMapperConfig);
		fb.setDataSource(solarConfigDataSource);

		return fb.getObject();
	}
}
