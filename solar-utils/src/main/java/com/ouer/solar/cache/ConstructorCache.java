/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.ClassUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.able.Computable;

/**
 * 用于缓存反射出来的<code>Constructor</code>，避免重复多次反射，提高性能
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-6-8 下午1:59:19
 */
public class ConstructorCache {

    /**
     * 搞个单例避免产生重复数据
     */
    private static final ConstructorCache instance = new ConstructorCache();

    /**
     * @see ConcurrentCache
     */
    private final Computable<String, Constructor<?>[]> cache = ConcurrentCache.createComputable();

    private final Computable<String, Constructor<?>> firstCache = ConcurrentCache.createComputable();

    private ConstructorCache() {

    }

    public static ConstructorCache getInstance() {
        return instance;
    }

    /**
     * 生成并缓存<code>clazz</code>的所有<code>Constructor</code>
     * 
     * @param clazz 目标class
     * @return <code>clazz</code>的所有<code>Constructor</code>
     */
    public Constructor<?>[] getConstructors(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Constructor<?>[]>() {
            @Override
            public Constructor<?>[] call() throws Exception {
                return ReflectionUtil.getAllConstructorsOfClass(clazz, true);
            }
        });

    }

    /**
     * 生成并缓存<code>clazz</code>的所有带<code>annotationClass</code>的 <code>Constructor</code>
     * 
     * @param clazz 目标class
     * @param annotationClass 指定注解
     * @return 所有带<code>annotationClass</code>的 <code>Constructor</code>
     */
    public Constructor<?>[] getConstructors(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz) + "." + annotationClass.getName(),
                new Callable<Constructor<?>[]>() {
                    @Override
                    public Constructor<?>[] call() throws Exception {
                        return ReflectionUtil.getAllConstructorsOfClass(clazz, true);
                    }
                });
    }

    /**
     * 生成并缓存<code>clazz</code>中名为<code>fieldName</code>的<code>Constructor</code>
     * 
     * @param clazz 目标class
     * @return 目标Field
     */
    public Constructor<?> getFirstConstructor(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return firstCache.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Constructor<?>>() {
            @Override
            public Constructor<?> call() throws Exception {
                Constructor<?>[] constructors = ReflectionUtil.getAllConstructorsOfClass(clazz, true);
                for (Constructor<?> constructor : constructors) {
                    Type[] types = constructor.getGenericParameterTypes();
                    if (ArrayUtil.isEmpty(types)) {
                        return constructor;
                    }
                }

                return constructors[0];
            }
        });

    }

}
