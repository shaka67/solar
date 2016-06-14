/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.mybatis;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EsMapperMethod {

    private final SSqlCommand command;
    private final MethodSignature method;

    public EsMapperMethod(Class<?> mapperInterface, Method method,
            Configuration config, String table) {
    	command = new SSqlCommand(config, mapperInterface, method, table);
    	this.method = new MethodSignature(config, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        if (SqlCommandType.INSERT == command.getType()) {
            final Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.insert(command.getName(), param));
        } else if (SqlCommandType.UPDATE == command.getType()) {
            final Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.update(command.getName(), param));
        } else if (SqlCommandType.DELETE == command.getType()) {
            final Object param = method.convertArgsToSqlCommandParam(args);
            result = rowCountResult(sqlSession.delete(command.getName(), param));
        } else if (SqlCommandType.SELECT == command.getType()) {
            if (method.returnsVoid() && method.hasResultHandler()) {
                executeWithResultHandler(sqlSession, args);
                result = null;
            } else if (method.returnsMany()) {
                result = executeForMany(sqlSession, args);
            } else if (method.returnsMap()) {
                result = executeForMap(sqlSession, args);
            } else {
                final Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.selectOne(command.getName(), param);
            }
        } else {
            throw new BindingException("Unknown execution method for: "
                    + command.getName());
        }
        if (result == null && method.getReturnType().isPrimitive()
                && !method.returnsVoid()) {
            throw new BindingException(
                    "Mapper method '"
                            + command.getName()
                            + " attempted to return null from a method with a primitive return type ("
                            + method.getReturnType() + ").");
        }
        return result;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType())
                || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType())
                || Long.TYPE.equals(method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(method.getReturnType())
                || Boolean.TYPE.equals(method.getReturnType())) {
            result = (rowCount > 0);
        } else {
            throw new BindingException("Mapper method '" + command.getName()
                    + "' has an unsupported return type: "
                    + method.getReturnType());
        }
        return result;
    }

    private void executeWithResultHandler(SqlSession sqlSession, Object[] args) {
        final MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(
                command.getName());
        if (void.class.equals(ms.getResultMaps().get(0).getType())) {
            throw new BindingException(
                    "method "
                            + command.getName()
                            + " needs either a @ResultMap annotation, a @ResultType annotation,"
                            + " or a resultType attribute in XML so a ResultHandler can be used as a parameter.");
        }
        final Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            final RowBounds rowBounds = method.extractRowBounds(args);
            sqlSession.select(command.getName(), param, rowBounds,
                    method.extractResultHandler(args));
        } else {
            sqlSession.select(command.getName(), param,
                    method.extractResultHandler(args));
        }
    }

    private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
        List<E> result;
        final Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            final RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.<E> selectList(command.getName(), param,
                    rowBounds);
        } else {
            result = sqlSession.<E> selectList(command.getName(), param);
        }
        // issue #510 Collections & arrays support
        if (!method.getReturnType().isAssignableFrom(result.getClass())) {
            if (method.getReturnType().isArray()) {
                return convertToArray(result);
            } else {
                return convertToDeclaredCollection(
                        sqlSession.getConfiguration(), result);
            }
        }
        return result;
    }

    private <E> Object convertToDeclaredCollection(Configuration config,
            List<E> list) {
        final Object collection = config.getObjectFactory().create(
                method.getReturnType());
        final MetaObject metaObject = config.newMetaObject(collection);
        metaObject.addAll(list);
        return collection;
    }

    @SuppressWarnings("unchecked")
    private <E> E[] convertToArray(List<E> list) {
        E[] array = (E[]) Array.newInstance(method.getReturnType()
                .getComponentType(), list.size());
        array = list.toArray(array);
        return array;
    }

    private <K, V> Map<K, V> executeForMap(SqlSession sqlSession, Object[] args) {
        Map<K, V> result;
        final Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            final RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.<K, V> selectMap(command.getName(), param,
                    method.getMapKey(), rowBounds);
        } else {
            result = sqlSession.<K, V> selectMap(command.getName(), param,
                    method.getMapKey());
        }
        return result;
    }

    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new BindingException("Parameter '" + key
                        + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }

    public static class SSqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SSqlCommand(Configuration configuration,
                Class<?> mapperInterface, Method method, String table)
                throws BindingException {
            final String statementName = mapperInterface.getName() + "."
                    + method.getName() + "_" + table;
            MappedStatement ms = null;
            if (configuration.hasStatement(statementName)) {
                ms = configuration.getMappedStatement(statementName);
            } else if (!mapperInterface.equals(method.getDeclaringClass())) { // issue #35
                final String parentStatementName = method.getDeclaringClass()
                        .getName() + "." + method.getName();
                if (configuration.hasStatement(parentStatementName)) {
                    ms = configuration.getMappedStatement(parentStatementName);
                }
            }
            if (ms == null) {
                throw new BindingException(
                        "Invalid bound statement (not found): " + statementName);
            }
            name = ms.getId();
            type = ms.getSqlCommandType();
            if (type == SqlCommandType.UNKNOWN) {
                throw new BindingException("Unknown execution method for: "
                        + name);
            }
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }

    public static class MethodSignature {

        private final boolean returnsMany;
        private final boolean returnsMap;
        private final boolean returnsVoid;
        private final Class<?> returnType;
        private final String mapKey;
        private final Integer resultHandlerIndex;
        private final Integer rowBoundsIndex;
        private final SortedMap<Integer, String> params;
        private final boolean hasNamedParameters;

        public MethodSignature(Configuration configuration, Method method)
                throws BindingException {
            returnType = method.getReturnType();
            returnsVoid = void.class.equals(returnType);
            returnsMany = (configuration.getObjectFactory().isCollection(
                    returnType) || returnType.isArray());
            mapKey = getMapKey(method);
            returnsMap = (mapKey != null);
            hasNamedParameters = hasNamedParams(method);
            rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
            resultHandlerIndex = getUniqueParamIndex(method,
                    ResultHandler.class);
            params = Collections.unmodifiableSortedMap(getParams(method,
                    hasNamedParameters));
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            final int paramCount = params.size();
            if (args == null || paramCount == 0) {
                return null;
            } else if (!hasNamedParameters && paramCount == 1) {
                return args[params.keySet().iterator().next()];
            } else {
                final Map<String, Object> param = new ParamMap<Object>();
                int i = 0;
                for (final Map.Entry<Integer, String> entry : params.entrySet()) {
                    param.put(entry.getValue(), args[entry.getKey()]);
                    // issue #71, add param names as param1, param2...but ensure
                    // backward compatibility
                    final String genericParamName = "param"
                            + String.valueOf(i + 1);
                    if (!param.containsKey(genericParamName)) {
                        param.put(genericParamName, args[entry.getKey()]);
                    }
                    i++;
                }
                return param;
            }
        }

        public boolean hasRowBounds() {
            return (rowBoundsIndex != null);
        }

        public RowBounds extractRowBounds(Object[] args) {
            return (hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null);
        }

        public boolean hasResultHandler() {
            return (resultHandlerIndex != null);
        }

        public ResultHandler extractResultHandler(Object[] args) {
            return (hasResultHandler() ? (ResultHandler) args[resultHandlerIndex]
                    : null);
        }

        public String getMapKey() {
            return mapKey;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public boolean returnsMany() {
            return returnsMany;
        }

        public boolean returnsMap() {
            return returnsMap;
        }

        public boolean returnsVoid() {
            return returnsVoid;
        }

        private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
            Integer index = null;
            final Class<?>[] argTypes = method.getParameterTypes();
            for (int i = 0; i < argTypes.length; i++) {
                if (paramType.isAssignableFrom(argTypes[i])) {
                    if (index == null) {
                        index = i;
                    } else {
                        throw new BindingException(method.getName()
                                + " cannot have multiple "
                                + paramType.getSimpleName() + " parameters");
                    }
                }
            }
            return index;
        }

        private String getMapKey(Method method) {
            String mapKey = null;
            if (Map.class.isAssignableFrom(method.getReturnType())) {
                final MapKey mapKeyAnnotation = method
                        .getAnnotation(MapKey.class);
                if (mapKeyAnnotation != null) {
                    mapKey = mapKeyAnnotation.value();
                }
            }
            return mapKey;
        }

        private SortedMap<Integer, String> getParams(Method method,
                boolean hasNamedParameters) {
            final SortedMap<Integer, String> params = new TreeMap<Integer, String>();
            final Class<?>[] argTypes = method.getParameterTypes();
            final Class<?>[] realArgTypes = Arrays.copyOfRange(argTypes, 1,
                    argTypes.length);
            // for (int i = 0; i < argTypes.length; i++) {
            for (int i = 0; i < realArgTypes.length; i++) { // very important
                if (!RowBounds.class.isAssignableFrom(realArgTypes[i])
                        && !ResultHandler.class
                                .isAssignableFrom(realArgTypes[i])) {
                    String paramName = String.valueOf(params.size());
                    if (hasNamedParameters) {
                        paramName = getParamNameFromAnnotation(method, i,
                                paramName);
                    }
                    params.put(i, paramName);
                }
            }
            return params;
        }

        private String getParamNameFromAnnotation(Method method, int i,
                String paramName) {
            final Object[] paramAnnos = method.getParameterAnnotations()[i];
            for (final Object paramAnno : paramAnnos) {
                if (paramAnno instanceof Param) {
                    paramName = ((Param) paramAnno).value();
                }
            }
            return paramName;
        }

        private boolean hasNamedParams(Method method) {
            boolean hasNamedParams = false;
            final Object[][] paramAnnos = method.getParameterAnnotations();
            for (final Object[] paramAnno : paramAnnos) {
                for (final Object aParamAnno : paramAnno) {
                    if (aParamAnno instanceof Param) {
                        hasNamedParams = true;
                        break;
                    }
                }
            }
            return hasNamedParams;
        }

    }

}
