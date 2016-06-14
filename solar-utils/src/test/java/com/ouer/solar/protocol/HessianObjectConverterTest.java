/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.protocol.HessianObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class HessianObjectConverterTest {

    @Test
    public void convertNull() throws IOException {
        final ObjectConverter converter = new HessianObjectConverter();
        final String data = converter.toString(null);
        assertNull(data);
        final Object object = converter.fromString(data, Object.class);
        assertNull(object);
    }

    @Test
    public void convertObject() throws IOException {
        final Pojo pojo = Pojo.create();

        final ObjectConverter converter = new HessianObjectConverter();
        final String value = converter.toString(pojo);
        assertNotNull(value);

        final Pojo another = converter.fromString(value, Pojo.class);
        assertNotNull(another);
        assertEquals(pojo.property1, another.property1);
        assertEquals(pojo.property2, another.property2);
        assertEquals(pojo.property3, another.property3);
        assertEquals(pojo.property4, another.property4);

        assertNotNull(another.left);
        assertEquals(pojo.left.property1, another.left.property1);
        assertEquals(pojo.left.property2, another.left.property2);
        assertEquals(pojo.left.property3, another.left.property3);
        assertEquals(pojo.left.property4, another.left.property4);

        assertNotNull(another.right);
        assertEquals(pojo.right.property1, another.right.property1);
        assertEquals(pojo.right.property2, another.right.property2);
        assertEquals(pojo.right.property3, another.right.property3);
        assertEquals(pojo.right.property4, another.right.property4);
    }

    @Test
    public void convertArray() throws IOException {
        final Object[] array = new Object[] { Pojo.create(), Pojo.create() };
        final ObjectConverter converter = new HessianObjectConverter();
        final String data = converter.toString(array);
        assertNotNull(data);

        final Object[] another = converter.fromString(data, Object[].class);
        assertNotNull(another);
        assertEquals(2, another.length);
        assertEquals(Pojo.class, another[0].getClass());
        assertEquals(Pojo.class, another[1].getClass());
    }

    @Test
    public void convertSortedMap() throws IOException {
        final SortedMap<String, Pojo> map = Maps.newTreeMap();
        final ObjectConverter converter = new HessianObjectConverter();
        final String data = converter.toString(map);
        assertNotNull(data);

        final SortedMap<String, Pojo> another = converter.fromString(data, SortedMap.class);
        assertNotNull(another);
    }

    @SuppressWarnings("serial")
    static class Pojo implements Serializable
    {
        String property1;
        Object property2;
        final List<Object> property3;
        final Map<String, Object> property4;
        Pojo left;
        Pojo right;

        public static Pojo create()
        {
            final Pojo left = new Pojo();
            left.property1 = "left";
            left.property2 = new Date();
            left.addProperty3(new Date());
            left.putProperty4("left key", new Date());

            final Pojo right = new Pojo();
            right.property1 = "right";
            right.property2 = new Date();
            right.addProperty3(new Date());
            right.putProperty4("right key", new Date());

            final Pojo pojo = new Pojo();
            pojo.property1 = "parent";
            pojo.property2 = new Date();
            pojo.addProperty3(new Date());
            pojo.putProperty4("parent key", new Date());
            pojo.left = left;
            pojo.right = right;

            return pojo;
        }

        Pojo() {
            property3 = Lists.newArrayList();
            property4 = Maps.newHashMap();
        }

        public String getProperty1() {
            return property1;
        }

        public void setProperty1(String property1) {
            this.property1 = property1;
        }

        public Object getProperty2() {
            return property2;
        }

        public void setProperty2(Object property2) {
            this.property2 = property2;
        }

        public List<Object> getProperty3() {
            return property3;
        }


        public void addProperty3(Object obj) {
            property3.add(obj);
        }

        public Map<String, Object> getProperty4() {
            return property4;
        }

        public void putProperty4(String key, Object value) {
            property4.put(key, value);
        }

        public Pojo getLeft() {
            return left;
        }

        public void setLeft(Pojo left) {
            this.left = left;
        }

        public Pojo getRight() {
            return right;
        }

        public void setRight(Pojo right) {
            this.right = right;
        }
    }

}