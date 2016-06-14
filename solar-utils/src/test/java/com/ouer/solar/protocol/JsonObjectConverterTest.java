/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.protocol.JsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;

/**
 * FIXME: JsonObjectConverter still has issues while parsing String back to object that
 * has nested collection type, like the one we demonstrated in this test case.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JsonObjectConverterTest {

    @Test
    public void convertNull() throws IOException {
        final ObjectConverter converter = new JsonObjectConverter();
        final String data = converter.toString(null);
        assertNull(data);
        final Object object = converter.fromString(data, Object.class);
        assertNull(object);
    }

//    @Test
    public void convertObject() throws IOException {
        final Pojo pojo = Pojo.create();

        final ObjectConverter converter = new JsonObjectConverter();
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

//    @Test
    public void convertArray() throws IOException {
        final Object[] array = new Object[] { Pojo.create(), Pojo.create() };
        final ObjectConverter converter = new JsonObjectConverter();
        final String data = converter.toString(array);
        assertNotNull(data);

        final Object[] another = converter.fromString(data, Object[].class);
        assertNotNull(another);
        assertEquals(2, another.length);
        assertEquals(Pojo.class, another[0].getClass());
        assertEquals(Pojo.class, another[1].getClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void convertList() throws IOException {
        final ObjectConverter converter = new JsonObjectConverter();

        final List<String> list1 = Lists.newArrayList("value1", "value2", "value3");
        final String value1 = converter.toString(list1);
        assertNotNull(value1);
        final List<String> another1 = converter.fromString(value1, List.class);
        assertNotNull(another1);
        assertEquals(list1.get(0), another1.get(0));
        assertEquals(list1.get(1), another1.get(1));
        assertEquals(list1.get(2), another1.get(2));

        final List<Object> list2 = Lists.newArrayList();
        list2.add("value1");
        list2.add(new Date());
        list2.add(Integer.valueOf(1234));
        final String value2 = converter.toString(list2);
        final List<Object> another2 = converter.fromString(value2, List.class);
        assertNotNull(another2);
        assertEquals(list2.get(0), another2.get(0));
        assertEquals(list2.get(1), another2.get(1));
        assertEquals(list2.get(2), another2.get(2));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void convertMap() throws IOException {
        final ObjectConverter converter = new JsonObjectConverter();

        final Map<String, String> map1 = Maps.newHashMap();
        map1.put("key1", "value1");
        final String value1 = converter.toString(map1);
        final Map<String, String> another1 = converter.fromString(value1, Map.class);
        assertNotNull(another1);
        assertEquals(map1.get("key1"), another1.get("key1"));

        final Map<String, Object> map2 = Maps.newHashMap();
        map2.put("key2", new Date());
        final String value2 = converter.toString(map2);
        final Map<String, Object> another2 = converter.fromString(value2, Map.class);
        assertNotNull(another2);
        assertEquals(map2.get("key2"), another2.get("key2"));
    }

    public static class Pojo
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