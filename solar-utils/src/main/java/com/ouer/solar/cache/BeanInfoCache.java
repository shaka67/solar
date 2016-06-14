/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.ExceptionUtil;
import com.ouer.solar.able.Computable;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午6:40:00
 */
public class BeanInfoCache extends CachedLogger {

    private static final BeanInfoCache instance = new BeanInfoCache();

    private final Computable<String, Map<String, PropertyDescriptor>> propertyCached = ConcurrentCache
            .createComputable();

    private final Computable<String, Map<String, MethodDescriptor>> methodCached = ConcurrentCache.createComputable();

    private BeanInfoCache() {

    }

    public static BeanInfoCache getInstance() {
        return instance;
    }

    public BeanInfo getBeanInfo(Class<?> beanClass) {
        try {
            return Introspector.getBeanInfo(beanClass, Object.class);
        } catch (IntrospectionException e) {
            logger.error("Introspector.getBeanInfo {} error", e, beanClass);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass) {
        try {
            return Introspector.getBeanInfo(beanClass, stopClass);
        } catch (IntrospectionException e) {
            logger.error("Introspector.getBeanInfo {} error", e, beanClass);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public BeanDescriptor getBeanDescriptor(Class<?> beanClass) {
        BeanInfo beanInfo = getBeanInfo(beanClass);

        return beanInfo.getBeanDescriptor();
    }

    private Map<String, PropertyDescriptor> getPropertyMap(final Class<?> beanClass, final Class<?> stopClass) {
        String key = (stopClass == null) ? beanClass.getName() : beanClass.getName() + "_" + stopClass.getName();

        return propertyCached.get(key, new Callable<Map<String, PropertyDescriptor>>() {
            @Override
            public Map<String, PropertyDescriptor> call() throws Exception {
                BeanInfo beanInfo = getBeanInfo(beanClass, stopClass);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                Map<String, PropertyDescriptor> map = CollectionUtil.createHashMap(propertyDescriptors.length);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    map.put(propertyDescriptor.getName(), propertyDescriptor);
                }

                return map;
            }
        });
    }

    public Map<String, PropertyDescriptor> getPropertyDescriptor(final Class<?> beanClass) {
        Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, null);

        return Collections.unmodifiableMap(map);
    }

    public Map<String, PropertyDescriptor> getPropertyDescriptor(final Class<?> beanClass, final Class<?> stopClass) {
        Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, stopClass);

        return Collections.unmodifiableMap(map);
    }

    public PropertyDescriptor getPropertyDescriptor(final Class<?> beanClass, String propertyName) {
        Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, null);
        return map.get(propertyName);
    }

    private Map<String, MethodDescriptor> getMethodMap(final Class<?> beanClass, final Class<?> stopClass) {
        String key = (stopClass == null) ? beanClass.getName() : beanClass.getName() + "_" + stopClass.getName();

        return methodCached.get(key, new Callable<Map<String, MethodDescriptor>>() {
            @Override
            public Map<String, MethodDescriptor> call() throws Exception {
                BeanInfo beanInfo = getBeanInfo(beanClass, stopClass);
                MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
                Map<String, MethodDescriptor> map = CollectionUtil.createHashMap(methodDescriptors.length);
                for (MethodDescriptor methodDescriptor : methodDescriptors) {
                    map.put(methodDescriptor.getName(), methodDescriptor);
                }

                return map;
            }
        });
    }

    public Map<String, MethodDescriptor> getMethodDescriptor(final Class<?> beanClass, final Class<?> stopClass) {
        Map<String, MethodDescriptor> map = getMethodMap(beanClass, stopClass);

        return Collections.unmodifiableMap(map);
    }

    public Map<String, MethodDescriptor> getMethodDescriptor(final Class<?> beanClass) {
        Map<String, MethodDescriptor> map = getMethodMap(beanClass, null);

        return Collections.unmodifiableMap(map);
    }

    public MethodDescriptor getMethodDescriptor(final Class<?> beanClass, String methodName) {
        Map<String, MethodDescriptor> map = getMethodMap(beanClass, null);

        return map.get(methodName);
    }

}
