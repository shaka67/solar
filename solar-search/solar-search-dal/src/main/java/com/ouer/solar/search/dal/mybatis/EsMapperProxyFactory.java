/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

import com.ouer.solar.search.dal.mybatis.EsMapperProxy.MethodAndTable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsMapperProxyFactory<T> extends MapperProxyFactory<T> {

//    private final Map<Method, EsMapperMethod> methodCache = new ConcurrentHashMap<Method, EsMapperMethod>();
    private final Map<MethodAndTable, EsMapperMethod> methodCache = new ConcurrentHashMap<MethodAndTable, EsMapperMethod>();

    public EsMapperProxyFactory(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    public T newInstance(SqlSession sqlSession) {
        final EsMapperProxy<T> mapperProxy = new EsMapperProxy<T>(sqlSession,
                super.getMapperInterface(), methodCache);
        return newInstance(mapperProxy);
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(EsMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(
                getMapperInterface().getClassLoader(),
                new Class[] { getMapperInterface() }, mapperProxy);
    }

}
