/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;

import com.ouer.solar.ClassLoaderUtil;
import com.ouer.solar.RuntimeUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月21日 上午5:16:10
 */
public class RuntimeUtilTest extends CachedLogger {

    @Test
    public void currentClassMethod() {
        assertEquals("com.qf.common.RuntimeUtilTest.currentClassMethod", RuntimeUtil.currentClassMethod());
    }

    @Test
    public void currentMethodName() {
        assertEquals("currentMethodName", RuntimeUtil.currentMethodName());
    }

    @Test
    public void currentClassName() {
        assertEquals("com.qf.common.RuntimeUtilTest", RuntimeUtil.currentClassName());
    }

    @Test
    public void currentNamespace() {
        assertEquals("com.qf.common.RuntimeUtilTest.currentNamespace", RuntimeUtil.currentNamespace());
    }

    @Test
    public void fileNameAndLine() {
        logger.info(RuntimeUtil.fileNameAndLine());
    }

    @Test
    public void classLocation() {
        File file = new File(RuntimeUtil.classLocation());
        String classLocation = file.getAbsolutePath();

        file = new File(ClassLoaderUtil.getClasspath());
        String classpath = file.getAbsolutePath();

        String target = StringUtil.substringBeforeLast(classpath, File.separator);

        assertEquals(target + File.separator + "classes", classLocation);

        assertNull(RuntimeUtil.classLocation(null));

        assertEquals("java.lang.String", RuntimeUtil.classLocation(String.class));

        // logger.info(RuntimeUtil.classLocation(String.class));

        logger.info(RuntimeUtil.classLocation(StringUtil.class));
    }

}
