/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.cache.MethodCache;
import com.ouer.solar.sample.AnnotationClass;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午1:13:15
 */
public class MethodCacheTest {

    private MethodCache cache;

    private int methodSize;

    private int instanceMethodSize;

    @Before
    public void setUp() throws Exception {
        cache = MethodCache.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        cache = null;
    }

    @Test
    public void getMethods() {
        Method[] result = cache.getMethods(String.class);
        Method[] methods = ReflectionUtil.getAllMethodsOfClass(String.class);

        assertArrayEquals(result, methods);
        methodSize = result.length;

        result = cache.getMethods(AnnotationClass.class, AnnotationClass.TestAnnotation.class);
        methods =
                ReflectionUtil.getAnnotationMethods(AnnotationClass.class, AnnotationClass.TestAnnotation.class).toArray(
                        new Method[0]);

        assertArrayEquals(result, methods);
    }

    @Test
    public void getInstanceMethods() {
        Method[] result = cache.getInstanceMethods(String.class);
        Method[] fields = ReflectionUtil.getAllInstanceMethods(String.class);

        assertArrayEquals(result, fields);
        instanceMethodSize = result.length;

        result = cache.getInstanceMethods(AnnotationClass.class);
        fields = ReflectionUtil.getAllInstanceMethods(AnnotationClass.class);
        assertArrayEquals(result, fields);
        getMethods();
        assertTrue(methodSize > 0);
        assertTrue(instanceMethodSize > 0);
        assertTrue(methodSize > instanceMethodSize);
    }

}
