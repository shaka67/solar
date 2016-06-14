/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.test;

import java.io.File;

import com.ouer.solar.Assert;

/**
 * 用来初始化测试环境的静态辅助类。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月20日 上午3:56:33
 */
public class TestEnvStatic {
    private static final TestEnv env = new TestEnv().init();

    public static final File basedir = env.getBasedir();
    public static final File srcdir = env.getSrcdir();
    public static final File destdir = env.getDestdir();
    public static final File javaHome = TestUtil.getJavaHome();

    /** 假如单元测试类没有显式地引用任何常量，那么调用这个方法可以确保测试环境被初始化。 */
    public static void init() {
        Assert.assertNotNull(env);
    }
}
