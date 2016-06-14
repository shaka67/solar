/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import org.junit.Assert;
import org.junit.Test;

import com.ouer.solar.ResourceUtil;

/**
 * 内部类未测试 FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-13 下午8:07:42
 */
public class ResourceUtilTest {

    @Test
    public void getResourcePath() {
        Assert.assertNull(ResourceUtil.getResourcePath((Object) null, null));
        Assert.assertNull(ResourceUtil.getResourcePath((Object) null, "test.resource"));
        Assert.assertNull(ResourceUtil.getResourcePath(new Object(), null));
        Assert.assertEquals("java/lang/test.resource", ResourceUtil.getResourcePath(new Object(), "test.resource"));
        Assert.assertEquals("/test.resource", ResourceUtil.getResourcePath(new int[0], "test.resource"));

        Assert.assertNull(ResourceUtil.getResourcePath((Class<?>) null, null));
        Assert.assertNull(ResourceUtil.getResourcePath((Class<?>) null, "test.resource"));
        Assert.assertNull(ResourceUtil.getResourcePath(String.class, null));
        Assert.assertEquals("java/lang/test.resource", ResourceUtil.getResourcePath(String.class, "test.resource"));
        Assert.assertEquals("/test.resource", ResourceUtil.getResourcePath(int[].class, "test.resource"));

        Assert.assertNull(ResourceUtil.getResourcePath((String) null, null));
        Assert.assertNull(ResourceUtil.getResourcePath((String) null, "test.resource"));
        Assert.assertNull(ResourceUtil.getResourcePath("java.lang.String", null));
        Assert.assertNull(ResourceUtil.getResourcePath("this is a string", null));
        Assert.assertEquals("java/lang/test.resource",
                ResourceUtil.getResourcePath("java.lang.String", "test.resource"));
        Assert.assertEquals("/test.resource", ResourceUtil.getResourcePath("int", "test.resource"));
        Assert.assertEquals("/test.resource", ResourceUtil.getResourcePath("[I", "test.resource"));
        Assert.assertEquals("java/lang/test.resource",
                ResourceUtil.getResourcePath("[Ljava.lang.Integer;", "test.resource"));

    }

    @Test
    public void getPropertyPath() {
        Assert.assertNull(ResourceUtil.getPropertyPath((Object) null, null));
        Assert.assertNull(ResourceUtil.getPropertyPath((Object) null, "test"));
        Assert.assertNull(ResourceUtil.getPropertyPath(new Object(), null));
        Assert.assertEquals("java/lang/test.properties", ResourceUtil.getPropertyPath(new Object(), "test"));
        Assert.assertEquals("/test.properties", ResourceUtil.getPropertyPath(new int[0], "test"));

        Assert.assertNull(ResourceUtil.getPropertyPath((Class<?>) null, null));
        Assert.assertNull(ResourceUtil.getPropertyPath((Class<?>) null, "test"));
        Assert.assertNull(ResourceUtil.getPropertyPath(String.class, null));
        Assert.assertEquals("java/lang/test.properties", ResourceUtil.getPropertyPath(String.class, "test"));
        Assert.assertEquals("/test.properties", ResourceUtil.getPropertyPath(int[].class, "test"));

        Assert.assertNull(ResourceUtil.getPropertyPath((String) null, null));
        Assert.assertNull(ResourceUtil.getPropertyPath((String) null, "test"));
        Assert.assertNull(ResourceUtil.getPropertyPath("java.lang.String", null));
        Assert.assertNull(ResourceUtil.getPropertyPath("This is a string", null));
        Assert.assertEquals("java/lang/test.properties", ResourceUtil.getPropertyPath("java.lang.String", "test"));
        Assert.assertEquals("/test.properties", ResourceUtil.getPropertyPath("int", "test"));
        Assert.assertEquals("/test.properties", ResourceUtil.getPropertyPath("[I", "test"));
        Assert.assertEquals("java/lang/test.properties", ResourceUtil.getPropertyPath("[Ljava.lang.Integer;", "test"));

    }

    @Test
    public void getResourceNameForObject() {
        Assert.assertNull(ResourceUtil.getResourceNameForObject(null));
        Assert.assertEquals("java/lang/String.class", ResourceUtil.getResourceNameForObject("This is a string"));
        Assert.assertEquals("[Ljava/lang/Integer;.class", ResourceUtil.getResourceNameForObject(new Integer[0]));
        Assert.assertEquals("[I.class", ResourceUtil.getResourceNameForObject(new int[0]));
    }

    @Test
    public void getResourceNameForClass() {
        Assert.assertNull(ResourceUtil.getResourceNameForClass(null));
        Assert.assertEquals("java/lang/String.class", ResourceUtil.getResourceNameForClass(String.class));
        Assert.assertEquals("int.class", ResourceUtil.getResourceNameForClass(int.class));
        Assert.assertEquals("[I.class", ResourceUtil.getResourceNameForClass(int[].class));
        Assert.assertEquals("[Ljava/lang/Integer;.class", ResourceUtil.getResourceNameForClass(Integer[].class));

    }

    @Test
    public void getResourceNameForPackage() {
        Assert.assertNull(ResourceUtil.getResourceNameForPackage((Object) null));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage(new Object()));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage(new int[0]));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage(new Integer[0]));

        Assert.assertNull(ResourceUtil.getResourceNameForPackage((Class<?>) null));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage(String.class));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage(int.class));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage(int[].class));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage(Integer[].class));

        Assert.assertNull(ResourceUtil.getResourceNameForPackage((String) null));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage("This is a String"));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage("java.lang.String"));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage("int"));
        Assert.assertEquals("", ResourceUtil.getResourceNameForPackage("[I"));
        Assert.assertEquals("java/lang", ResourceUtil.getResourceNameForPackage("[Ljava.lang.Integer;"));
    }

    @Test
    public void getResourceNameFor() {
        Assert.assertNull(ResourceUtil.getResourceNameFor((Object) null));
        Assert.assertEquals("java/lang/String", ResourceUtil.getResourceNameFor("This is a string"));
        Assert.assertEquals("[Ljava/lang/Integer;", ResourceUtil.getResourceNameFor(new Integer[0]));
        Assert.assertEquals("[I", ResourceUtil.getResourceNameFor(new int[0]));

        Assert.assertNull(ResourceUtil.getResourceNameFor((Class<?>) null));
        Assert.assertEquals("java/lang/String", ResourceUtil.getResourceNameFor(String.class));
        Assert.assertEquals("int", ResourceUtil.getResourceNameFor(int.class));
        Assert.assertEquals("[I", ResourceUtil.getResourceNameFor(int[].class));
        Assert.assertEquals("[Ljava/lang/Integer;", ResourceUtil.getResourceNameFor(Integer[].class));
    }

}
