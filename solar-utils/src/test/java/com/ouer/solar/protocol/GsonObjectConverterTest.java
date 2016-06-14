/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.protocol.GsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class GsonObjectConverterTest {

    private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.getDateTimeInstance();

    private static void assertDateEquals(Date expected, Date actual) {
        assertEquals(DEFAULT_DATE_FORMAT.format(expected), DEFAULT_DATE_FORMAT.format(actual));
    }

    @Test
    public void convertNull() throws IOException {
        final ObjectConverter converter = new GsonObjectConverter();
        final String data = converter.toString(null);
        assertNull(data);
        final Object object = converter.fromString(data, Object.class);
        assertNull(object);
    }

    @Test
    public void convertObject() throws IOException {
        final Pojo pojo = Pojo.create();

        final ObjectConverter converter = new GsonObjectConverter();
        final String value = converter.toString(pojo);
        assertNotNull(value);

        final Pojo another = converter.fromString(value, Pojo.class);
        assertNotNull(another);
        assertEquals(pojo.property1, another.property1);
        assertDateEquals(pojo.property2, another.property2);
        assertDateEquals(pojo.property3.get(0), another.property3.get(0));

        assertNotNull(another.left);
        assertEquals(pojo.left.property1, another.left.property1);
        assertDateEquals(pojo.left.property2, another.left.property2);
        assertDateEquals(pojo.left.property3.get(0), another.left.property3.get(0));

        assertNotNull(another.right);
        assertEquals(pojo.right.property1, another.right.property1);
        assertDateEquals(pojo.right.property2, another.right.property2);
        assertDateEquals(pojo.right.property3.get(0), another.right.property3.get(0));
    }

    @Test
    public void convertArray() throws IOException {
        final Pojo[] array = new Pojo[] { Pojo.create(), Pojo.create() };
        final ObjectConverter converter = new GsonObjectConverter();
        final String data = converter.toString(array);
        assertNotNull(data);

        final Object[] another = converter.fromString(data, Pojo[].class);
        assertNotNull(another);
        assertEquals(2, another.length);
        assertEquals(Pojo.class, another[0].getClass());
        assertEquals(Pojo.class, another[1].getClass());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void convertList() throws IOException {
        final ObjectConverter converter = new GsonObjectConverter();

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
//        assertEquals(list2.get(1), another2.get(1));
//        assertEquals(list2.get(2), another2.get(2));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void convertMap() throws IOException {
        final ObjectConverter converter = new GsonObjectConverter();

        final Map<String, String> map1 = Maps.newHashMap();
        map1.put("key1", "value1");
        final String value1 = converter.toString(map1);
        final Map<String, String> another1 = converter.fromString(value1, Map.class);
        assertNotNull(another1);
        assertEquals(map1.get("key1"), another1.get("key1"));

        final Map<String, Date> map2 = Maps.newHashMap();
        map2.put("key2", new Date());
        final String value2 = converter.toString(map2);
        final Map<String, Date> another2 = converter.fromString(value2, Map.class);
        assertNotNull(another2);
//        assertDateEquals(map2.get("key2"), another2.get("key2"));
    }

    public static class Pojo
    {
        String property1;
        Date property2;
        final List<Date> property3;
        final Map<String, Date> property4;
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

        public void setProperty2(Date property2) {
            this.property2 = property2;
        }

        public List<Date> getProperty3() {
            return property3;
        }


        public void addProperty3(Date date) {
            property3.add(date);
        }

        public Map<String, Date> getProperty4() {
            return property4;
        }

        public void putProperty4(String key, Date value) {
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