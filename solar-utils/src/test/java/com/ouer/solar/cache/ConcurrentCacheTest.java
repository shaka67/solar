/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.able.Computable;
import com.ouer.solar.cache.ConcurrentCache;
import com.ouer.solar.logger.LoggerFactory;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 上午11:43:12
 */
public class ConcurrentCacheTest {

    private Computable<String, Object> cache;

    @Before
    public void setUp() throws Exception {
        cache = ConcurrentCache.createComputable();
    }

    @After
    public void tearDown() throws Exception {
        cache = null;
    }

//    @Test
//    public void testString() {
//        final String key = "com.qf.common";
//
//        Object result = cache.get(key, new Callable<Object>() {
//
//            @Override
//            public Object call() throws Exception {
//                return StringUtil.toCamelCase(key);
//            }
//        });
//
//        assertEquals("com.qf.common", result);
//    }

    @Test
    public void testObject() {
        final String key = "object";

        Object result1 = cache.get(key, new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return new Object();
            }
        });

        Object result2 = cache.get(key, new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return new Object();
            }
        });

        assertEquals(result1, result2);
    }

    @Test
    public void testLogger() {
        Object result = cache.get("logger", new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return LoggerFactory.getLogger(ConcurrentCacheTest.class);
            }
        });

        assertEquals(LoggerFactory.getLogger(ConcurrentCacheTest.class), result);
        assertTrue(LoggerFactory.getLogger(ConcurrentCacheTest.class) == result);
    }

}
