/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.PackageUtil;
import com.ouer.solar.ClassUtilTest.Inner;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月17日 下午4:22:00
 */
public class PackageUtilTest extends CachedLogger {

    @Test
    public void getClassesInPackage() throws IOException {
        // null
        assertNull(PackageUtil.getClassesInPackage(null));
        assertNull(PackageUtil.getClassesInPackage(""));
        assertNull(PackageUtil.getClassesInPackage("     "));

        assertNull(PackageUtil.getClassesInPackage(null, null, null));
        assertNull(PackageUtil.getClassesInPackage("", null, null));
        assertNull(PackageUtil.getClassesInPackage("     ", null, null));
        assertNull(PackageUtil
                .getClassesInPackage(null, Arrays.asList(new String[] { "com.qf.common" }), null));
        assertNull(PackageUtil.getClassesInPackage("", Arrays.asList(new String[] { "com.qf.common" }), null));
        assertNull(PackageUtil.getClassesInPackage("     ", Arrays.asList(new String[] { "com.qf.common" }),
                null));

        assertTrue(CollectionUtil.isEmpty(PackageUtil.getClassesInPackage("com.qf.common.xxx")));
        assertTrue(CollectionUtil.isEmpty(PackageUtil.getClassesInPackage("notexist")));

        List<String> utils = PackageUtil.getClassesInPackage("com.qf.common");
        assertTrue(CollectionUtil.isNotEmpty(utils));

        List<String> subUtils = PackageUtil.getClassesInPackage("com.qf.common.*");
        assertTrue(CollectionUtil.isNotEmpty(subUtils));
        assertTrue(CollectionUtil.isNotEmpty(utils));
        assertTrue(subUtils.size() > utils.size());
        assertTrue(subUtils.containsAll(utils));

        assertEquals(PackageUtil.getClassesInPackage("com.qf.common.*"),
                PackageUtil.getClassesInPackage("com.qf.common.*", null, null));
        assertEquals(PackageUtil.getClassesInPackage("com.qf.common.*"),
                PackageUtil.getClassesInPackage("com.qf.common.*", Arrays.asList(new String[] { ".*" }), null));

        assertTrue(CollectionUtil.isEmpty(PackageUtil.getClassesInPackage("com.qf.common.*",
                Arrays.asList(new String[] { ".*" }), Arrays.asList(new String[] { ".*" }))));
        assertTrue(CollectionUtil.isEmpty(PackageUtil.getClassesInPackage("com.qf.common.*", null,
                Arrays.asList(new String[] { ".*" }))));

        List<String> includeTests =
                PackageUtil.getClassesInPackage("com.qf.common.*", Arrays.asList(new String[] { ".*Test" }),
                        null);
        assertTrue(CollectionUtil.isNotEmpty(includeTests));
        List<String> excludeTests =
                PackageUtil.getClassesInPackage("com.qf.common.*", null,
                        Arrays.asList(new String[] { ".*Test" }));
        assertTrue(CollectionUtil.isNotEmpty(excludeTests));
        assertEquals(subUtils.size(), includeTests.size() + excludeTests.size());

        List<String> langs = PackageUtil.getClassesInPackage("org.slf4j");
        assertTrue(CollectionUtil.isNotEmpty(langs));
        List<String> subLangs = PackageUtil.getClassesInPackage("org.slf4j.*");
        assertTrue(CollectionUtil.isNotEmpty(subLangs));
        // FIXME
        assertEquals(subLangs, langs);
    }

    @Test
    public void getResourceInPackage() throws IOException {
        // null
        assertNull(PackageUtil.getResourceInPackage(null));
        assertNull(PackageUtil.getResourceInPackage(""));
        assertNull(PackageUtil.getResourceInPackage("     "));

        assertTrue(CollectionUtil.isEmpty(PackageUtil.getResourceInPackage("com.qf.common.xxx")));
        assertTrue(CollectionUtil.isEmpty(PackageUtil.getResourceInPackage("notexist")));
        List<String> utils = PackageUtil.getResourceInPackage("com.qf.common");
        assertTrue(CollectionUtil.isNotEmpty(utils));
        List<String> subUtils = PackageUtil.getResourceInPackage("com.qf.common.*");
        assertTrue(CollectionUtil.isNotEmpty(subUtils));
        assertEquals(utils, subUtils);

        List<String> langs = PackageUtil.getResourceInPackage("org.slf4j");
        assertTrue(CollectionUtil.isNotEmpty(langs));
        List<String> subLangs = PackageUtil.getResourceInPackage("org.slf4j.*");
        assertTrue(CollectionUtil.isNotEmpty(subLangs));
        // FIXME
        assertEquals(subLangs, langs);

    }

    @Test
    public void getPackage() {
        assertNull(PackageUtil.getPackage(null));

        assertEquals("java.lang", PackageUtil.getPackage(String.class).getName());

        assertEquals("com.qf.common", PackageUtil.getPackage(PackageUtil.class).getName());

    }

    @Test
    public void getPackageName() {
        // null
        assertEquals("", PackageUtil.getPackageName((Object) null));
        assertEquals("", PackageUtil.getPackageName((Class<?>) null));
        assertEquals("", PackageUtil.getPackageName((String) null));
        assertEquals("", PackageUtil.getPackageName("  "));

        // 数组
        assertGetPackageName("", new int[0]);
        assertGetPackageName("", new int[0][]);
        assertGetPackageName("", new long[0]);
        assertGetPackageName("", new long[0][]);
        assertGetPackageName("", new short[0]);
        assertGetPackageName("", new short[0][]);
        assertGetPackageName("", new byte[0]);
        assertGetPackageName("", new byte[0][]);
        assertGetPackageName("", new char[0]);
        assertGetPackageName("", new char[0][]);
        assertGetPackageName("", new boolean[0]);
        assertGetPackageName("", new boolean[0][]);
        assertGetPackageName("", new float[0]);
        assertGetPackageName("", new float[0][]);
        assertGetPackageName("", new double[0]);
        assertGetPackageName("", new double[0][]);
        assertGetPackageName("java.util", new List[0]);
        assertGetPackageName("java.util", new List[0][]);

        // 非数组
        assertGetPackageName("java.util", CollectionUtil.createArrayList());

        assertGetPackageName("com.qf.common", new Inner());
        assertGetPackageName("java.lang", new Integer[0]);

        // 内联类/本地类/匿名类
        assertGetPackageName("com.qf.common", new Inner());
        assertGetPackageName("com.qf.common", new Inner[0]);

        class Local {
        }

        assertGetPackageName("com.qf.common", new Local());
        assertGetPackageName("com.qf.common", new Local[0]);

        Object anonymous = new Serializable() {
            private static final long serialVersionUID = 4375828012902534105L;
        };

        assertGetPackageName("com.qf.common", anonymous);

        // 非法类名
        assertEquals("", PackageUtil.getPackageName(""));
        assertEquals("", PackageUtil.getPackageName("["));
        assertEquals("", PackageUtil.getPackageName("[["));
        assertEquals("", PackageUtil.getPackageName("[[X"));
        assertEquals("", PackageUtil.getPackageName("[[L"));
        assertEquals("", PackageUtil.getPackageName("[[L;"));
        assertEquals("", PackageUtil.getPackageName("[[Lx"));
    }

    private void assertGetPackageName(String result, Object object) {
        // object
        assertEquals(result, PackageUtil.getPackageName(object));

        // class
        assertEquals(result, PackageUtil.getPackageName(object.getClass()));

        // class name
        assertEquals(result, PackageUtil.getPackageName(object.getClass().getName()));
        assertEquals(result, PackageUtil.getPackageName("  " + object.getClass().getName() + "  ")); // trim
    }
}
