/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.google.common.base.Objects;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsMapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
//    private final Map<Method, EsMapperMethod> methodCache;
    private final Map<MethodAndTable, EsMapperMethod> methodCache;

    public EsMapperProxy(SqlSession sqlSession, Class<T> mapperInterface,
            Map<MethodAndTable, EsMapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "the args size must greater than 0");
        }
        final EsMapperMethod mapperMethod = cachedMapperMethod(method, args[0]);
        Object[] real = null;
        if (args.length > 1) {
            real = Arrays.copyOfRange(args, 1, args.length);
        }
        return mapperMethod.execute(sqlSession, real);
    }

    private EsMapperMethod cachedMapperMethod(Method method, Object arg) {
        EsMapperMethod mapperMethod = methodCache.get(new MethodAndTable(method, (String) arg));
        if (mapperMethod == null) {
            mapperMethod = new EsMapperMethod(mapperInterface, method,
                    sqlSession.getConfiguration(), (String) arg);
            methodCache.put(new MethodAndTable(method, (String) arg), mapperMethod);
        }
        return mapperMethod;
    }

    static class MethodAndTable {

    	private final Method method;
    	private final String table;

    	public MethodAndTable(Method method, String table) {
    		this.method = method;
    		this.table = table;
    	}

		public Method getMethod() {
			return method;
		}

		public String getTable() {
			return table;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(method, table);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
	            return true;
	        }
	        if (obj == null || !obj.getClass().equals(getClass())) {
	            return false;
	        }
	        final MethodAndTable another = (MethodAndTable) obj;
	        return Objects.equal(method, another.method)
	        		&& Objects.equal(table, another.table);
		}

    }
}
