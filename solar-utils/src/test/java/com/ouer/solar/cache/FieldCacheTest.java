/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.cache.FieldCache;
import com.ouer.solar.sample.AnnotationClass;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午12:32:05
 */
public class FieldCacheTest {

    private FieldCache cache;

    private int fieldSize;

    private int instanceFieldSize;

    @Before
    public void setUp() throws Exception {
        cache = FieldCache.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        cache = null;
    }

    @Test
    public void getFields() {
        Field[] result = cache.getFields(String.class);
        Field[] fields = ReflectionUtil.getAllFieldsOfClass(String.class);

        assertArrayEquals(result, fields);
        fieldSize = result.length;

        result = cache.getFields(AnnotationClass.class, AnnotationClass.TestAnnotation.class);
        fields = ReflectionUtil.getAnnotationFields(AnnotationClass.class, AnnotationClass.TestAnnotation.class);

        assertArrayEquals(result, fields);
    }

    @Test
    public void getInstanceFields() {
        Field[] result = cache.getInstanceFields(String.class);
        Field[] fields = ReflectionUtil.getAllInstanceFields(String.class);

        assertArrayEquals(result, fields);
        instanceFieldSize = result.length;

        result = cache.getInstanceFields(AnnotationClass.class);
        fields = ReflectionUtil.getAllInstanceFields(AnnotationClass.class);
        assertArrayEquals(result, fields);
        getFields();
        assertTrue(fieldSize > 0);
        assertTrue(instanceFieldSize > 0);
        assertTrue(fieldSize > instanceFieldSize);
    }

}
