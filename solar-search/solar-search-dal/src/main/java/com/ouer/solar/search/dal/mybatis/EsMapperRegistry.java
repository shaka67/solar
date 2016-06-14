/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsMapperRegistry extends MapperRegistry {

    private final EsConfiguration config;
    private final Map<Class<?>, EsMapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, EsMapperProxyFactory<?>>();
    
    public EsMapperRegistry(Configuration config) {
        super(config);
        this.config = (EsConfiguration) config;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final EsMapperProxyFactory<T> mapperProxyFactory = (EsMapperProxyFactory<T>) knownMappers
                .get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type
                    + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (final Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: "
                    + e, e);
        }
    }

    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                throw new BindingException("Type " + type
                        + " is already known to the MapperRegistry.");
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new EsMapperProxyFactory<T>(type));
                // It's important that the type is added before the parser is
                // run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                final MapperAnnotationBuilder parser = new MapperAnnotationBuilder(
                        config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }

    @Override
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        final ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        final Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for (final Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }

    @Override
    public void addMappers(String packageName) {
        addMappers(packageName, Object.class);
    }

}
