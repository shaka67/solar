/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.collection.ArrayHashMap;
import com.ouer.solar.collection.DefaultMapEntry;
import com.ouer.solar.collection.ListMap;
import com.ouer.solar.collection.ArrayHashMapTest.Constructors;
import com.ouer.solar.collection.ArrayHashMapTest.ListMapBasic;
import com.ouer.solar.collection.ArrayHashMapTest.ListMapEntryList;
import com.ouer.solar.collection.ArrayHashMapTest.ListMapKeyList;
import com.ouer.solar.collection.ArrayHashMapTest.ListMapValueList;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午1:25:31
 */
@RunWith(Suite.class)
@SuiteClasses({ Constructors.class, ListMapBasic.class, ListMapEntryList.class, ListMapKeyList.class,
        ListMapValueList.class })
public class ArrayHashMapTest {
    private static int getThreshold(ArrayHashMap<Object, Object> map) {
        return map.getThreshold();
    }

    private static int getCapacity(ArrayHashMap<Object, Object> map) {
        return map.getCapacity();
    }

    public static class ListMapBasic extends AbstractListMapTest {
        @Override
        protected ListMap<Object, Object> createListMap() {
            return new ArrayHashMap<Object, Object>();
        }

        @Override
        @SuppressWarnings("unchecked")
        protected ListMap<Object, Object> cloneListMap(ListMap<Object, Object> map) {
            return (ListMap<Object, Object>) ((ArrayHashMap<Object, Object>) map).clone();
        }
    }

    public static class ListMapEntryList extends AbstractListMapViewTest {
        @Override
        protected ListMap<Object, Object> createListMap() {
            return new ArrayHashMap<Object, Object>();
        }

        @Override
        protected List<?> getView(ListMap<Object, Object> map) {
            return map.entryList();
        }

        @Override
        protected Object createItem(Object key, Object value) {
            return new DefaultMapEntry<Object, Object>(key, value);
        }
    }

    public static class ListMapKeyList extends AbstractListMapViewTest {
        @Override
        protected ListMap<Object, Object> createListMap() {
            return new ArrayHashMap<Object, Object>();
        }

        @Override
        protected List<?> getView(ListMap<Object, Object> map) {
            return map.keyList();
        }

        @Override
        protected Object createItem(Object key, Object value) {
            return key;
        }
    }

    public static class ListMapValueList extends AbstractListMapViewTest {
        @Override
        protected ListMap<Object, Object> createListMap() {
            return new ArrayHashMap<Object, Object>();
        }

        @Override
        protected List<?> getView(ListMap<Object, Object> map) {
            return map.valueList();
        }

        @Override
        protected Object createItem(Object key, Object value) {
            return value;
        }
    }

    public static class Constructors {
        private ArrayHashMap<Object, Object> map;

        /** 测试构造函数(initialCapacity, loadFactor). */
        @Test
        public void constructor1() {
            assertConstructorException(-1, .75f);
            assertConstructorException(16, 0);
            assertConstructorException(16, -1);

            map = new ArrayHashMap<Object, Object>(0, .75f);
            assertParameters(0, 1);

            map = new ArrayHashMap<Object, Object>(16, 1);
            assertParameters(16, 16);

            map = new ArrayHashMap<Object, Object>(16, 1.000001f);
            assertParameters(16, 16);

            map = new ArrayHashMap<Object, Object>(16, 2);
            assertParameters(32, 16);
        }

        /** 测试构造函数(initialCapacity). */
        @Test
        public void constructor2() {
            assertConstructorException(-1);

            map = new ArrayHashMap<Object, Object>(0);
            assertParameters(0, 1);
        }

        /** 测试构造函数(无参数). */
        @Test
        public void constructor3() {
            map = new ArrayHashMap<Object, Object>();
            assertParameters(12, 16);
        }

        /** 测试构造函数(Map). */
        @Test
        public void constructor4() {
            Map<Object, Object> anotherMap = CollectionUtil.createHashMap();

            anotherMap.put("aaa", "111");
            anotherMap.put("bbb", "222");
            anotherMap.put("ccc", "333");

            map = new ArrayHashMap<Object, Object>(anotherMap);
            assertParameters(12, 16);
            assertEquals(3, map.size());
            assertEquals("111", map.get("aaa"));
            assertEquals("222", map.get("bbb"));
            assertEquals("333", map.get("ccc"));
        }

        /**
         * 测试内部参数: threshold和capacity.
         * 
         * @param expectedThreshold 期望的阈值
         * @param expectedCapacity 期望的容量
         */
        private void assertParameters(int expectedThreshold, int expectedCapacity) {
            assertEquals(expectedThreshold, getThreshold(map));
            assertEquals(expectedCapacity, getCapacity(map));
        }

        /**
         * 测试并试图得到Exception.
         * 
         * @param initialCapacity 初始容量
         * @param loadFactor 负载系数
         */
        private void assertConstructorException(int initialCapacity, float loadFactor) {
            try {
                new ArrayHashMap<Object, Object>(initialCapacity, loadFactor);
                fail("should throw an IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
        }

        /**
         * 测试并试图得到Exception.
         * 
         * @param initialCapacity 初始容量
         */
        private void assertConstructorException(int initialCapacity) {
            try {
                new ArrayHashMap<Object, Object>(initialCapacity);
                fail("should throw an IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
