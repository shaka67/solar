/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.thirdparty;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import com.ouer.solar.config.DynamicConfigLocator;
import com.ouer.solar.config.spring.ConfigDBLocatorSpring;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.sms.thirdparty.mandao.MandaoAppIdSSNMapper;
import com.ouer.solar.sms.thirdparty.mandao.MandaoConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@Import({ConfigDBLocatorSpring.class, ConfigDalStoreSpring.class})
@ImportResource("classpath:/META-INF/applicationContext-sms-thirdparty.xml")
public class ThirdpartySpringConfig {

	@Autowired
	@Bean
	MandaoConfigLocator mandaoConfigLocator(DynamicConfigLocator dcLocator, MandaoAppIdSSNMapper mapper) {
		return new MandaoConfigLocator(dcLocator, mapper);
	}

	@Value("classpath:config/thirdpartyMapperConfig.xml")
	Resource thirdpartyMybatisMapperConfig;

	@Autowired
	DataSource thirdpartyDataSource;

	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz) throws Exception {
		final MapperFactoryBean<T> b = new MapperFactoryBean<T>();
		b.setMapperInterface(clazz);
		b.setSqlSessionFactory(sqlSessionFactory());
		return b;
	}

	@Bean
    public MandaoAppIdSSNMapper mandaoAppIdSSNMapper() throws Exception {
        return newMapperFactoryBean(MandaoAppIdSSNMapper.class).getObject();
    }

	@Bean(name = "thirdpartySqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		final SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(thirdpartyMybatisMapperConfig);
		fb.setDataSource(thirdpartyDataSource);
		return fb.getObject();
	}

}
