/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.ClassUtil;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.able.Computable;

/**
 * method cache
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午12:32:21
 */
public class MethodCache {

    /**
     * 搞个单例避免产生重复数据
     */
    private static final MethodCache INSTANCE = new MethodCache();

    private final Computable<String, Method[]> cache = ConcurrentCache.createComputable();

    private final Computable<String, Map<String, Method>> cachedMap = ConcurrentCache.createComputable();

    private MethodCache() {

    }

    public static MethodCache getInstance() {
        return INSTANCE;
    }

    /**
     * 生成并缓存<code>clazz</code>的所有<code>Method</code>
     * 
     * @param clazz 目标class
     * @return <code>clazz</code>的所有<code>Method</code>
     */
    public Method[] getMethods(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Method[]>() {
            @Override
            public Method[] call() throws Exception {
                return ReflectionUtil.getAllMethodsOfClass(clazz, true);
            }
        });

    }

    /**
     * 生成并缓存<code>clazz</code>的所有带<code>annotationClass</code>的 <code>Field</code>
     * 
     * @param clazz 目标class
     * @param annotationClass 指定注解
     * @return 所有带<code>annotationClass</code>的 <code>Field</code>
     */
    public Method[] getMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz) + "." + annotationClass.getName(),
                new Callable<Method[]>() {
                    @Override
                    public Method[] call() throws Exception {
                        return ReflectionUtil.getAnnotationMethods(clazz, annotationClass).toArray(new Method[0]);
                    }
                });
    }

    /**
     * 生成并缓存<code>clazz</code>的所有实例<code>Method</code>
     * 
     * @param clazz 目标class
     * @return <code>clazz</code>的所有实例<code>Method</code>
     */
    public Method[] getInstanceMethods(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz) + ".instance", new Callable<Method[]>() {
            @Override
            public Method[] call() throws Exception {
                return ReflectionUtil.getAllInstanceMethods(clazz, true);
            }
        });
    }

    /**
     * 生成并缓存<code>clazz</code>的所有实例的Public类型<code>Method</code>
     * 
     * @param clazz 目标class
     * @return <code>clazz</code>的所有实例的Public类型<code>Method</code>
     */
    public Method[] getInstancePublicMethods(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return cache.get(ClassUtil.getFriendlyClassName(clazz) + ".instance.public", new Callable<Method[]>() {
            @Override
            public Method[] call() throws Exception {
                List<Method> list = CollectionUtil.createArrayList();
                Method[] methods = ReflectionUtil.getAllInstanceMethods(clazz, true);
                for (Method method : methods) {
                    if (Modifier.isPublic(method.getModifiers())) {
                        list.add(method);
                    }
                }
                return list.toArray(new Method[0]);
            }
        });
    }

    private static String getMethodSignature(String methodName, Class<?>...params) {
        String name = methodName + (ArrayUtil.isEmpty(params) ? "" : ArrayUtil.toString(params));

        return name;
    }

    public Method getMethod(final Class<?> clazz, String methodName, Class<?>...params) {
        if (clazz == null) {
            return null;
        }

        Map<String, Method> map =
                cachedMap.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Map<String, Method>>() {
                    @Override
                    public Map<String, Method> call() throws Exception {
                        Method[] methods = getMethods(clazz);
                        Map<String, Method> methodMap = CollectionUtil.createHashMap(methods.length);

                        for (Method method : methods) {
                            Class<?>[] types = method.getParameterTypes();
                            String name = getMethodSignature(method.getName(), types);
                            methodMap.put(name, method);
                        }

                        return methodMap;
                    }
                });

        return map.get(getMethodSignature(methodName, params));
    }

    public Method getMethod(final Class<?> clazz, final Class<? extends Annotation> annotationClass, String methodName,
            Class<?>...params) {
        if (clazz == null) {
            return null;
        }

        Map<String, Method> map =
                cachedMap.get(ClassUtil.getFriendlyClassName(clazz) + "." + annotationClass.getName(),
                        new Callable<Map<String, Method>>() {
                            @Override
                            public Map<String, Method> call() throws Exception {
                                Method[] methods = getMethods(clazz, annotationClass);
                                Map<String, Method> methodMap = CollectionUtil.createHashMap(methods.length);

                                for (Method method : methods) {
                                    Class<?>[] types = method.getParameterTypes();
                                    String name = getMethodSignature(method.getName(), types);
                                    methodMap.put(name, method);
                                }

                                return methodMap;
                            }
                        });

        return map.get(getMethodSignature(methodName, params));
    }

    public Method getInstanceMethod(final Class<?> clazz, String methodName, Class<?>...params) {
        if (clazz == null) {
            return null;
        }

        Map<String, Method> map =
                cachedMap.get(ClassUtil.getFriendlyClassName(clazz) + ".instance", new Callable<Map<String, Method>>() {
                    @Override
                    public Map<String, Method> call() throws Exception {
                        Method[] methods = getInstanceMethods(clazz);
                        Map<String, Method> methodMap = CollectionUtil.createHashMap(methods.length);

                        for (Method method : methods) {
                            Class<?>[] types = method.getParameterTypes();
                            String name = getMethodSignature(method.getName(), types);
                            methodMap.put(name, method);
                        }

                        return methodMap;
                    }
                });

        return map.get(getMethodSignature(methodName, params));
    }

}
