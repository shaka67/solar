/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsConfiguration extends Configuration {

    public EsConfiguration() {
        super();
        mapperRegistry = new EsMapperRegistry(this);
    }

    public EsConfiguration(Environment environment) {
        super(environment);
        mapperRegistry = new EsMapperRegistry(this);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        mapperRegistry.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    @Override
    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }
}
