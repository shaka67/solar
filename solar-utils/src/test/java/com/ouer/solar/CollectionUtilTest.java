/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static com.ouer.solar.CollectionUtil.asList;
import static com.ouer.solar.CollectionUtil.createArrayHashMap;
import static com.ouer.solar.CollectionUtil.createArrayHashSet;
import static com.ouer.solar.CollectionUtil.createArrayList;
import static com.ouer.solar.CollectionUtil.createConcurrentMap;
import static com.ouer.solar.CollectionUtil.createHashMap;
import static com.ouer.solar.CollectionUtil.createHashSet;
import static com.ouer.solar.CollectionUtil.createLinkedHashMap;
import static com.ouer.solar.CollectionUtil.createLinkedHashSet;
import static com.ouer.solar.CollectionUtil.createLinkedList;
import static com.ouer.solar.CollectionUtil.createTreeMap;
import static com.ouer.solar.CollectionUtil.createTreeSet;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.ouer.solar.collection.ArrayHashMap;
import com.ouer.solar.collection.ArrayHashSet;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月20日 上午3:03:31
 */
public class CollectionUtilTest {
    @Test
    public void createArrayList_noArgs() {
        List<Integer> list = createArrayList();
        assertTrue(list instanceof ArrayList<?>);
    }

    @Test
    public void createArrayList_capacity() {
        List<Integer> list = createArrayList(2);
        assertTrue(list instanceof ArrayList<?>);
    }

    @Test
    public void createArrayList_iterable() {
        // collection
        List<Integer> list = createArrayList(Arrays.asList(1, 2, 3));
        assertTrue(list instanceof ArrayList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());

        // iteratable
        list = createArrayList(new SimpleIterable<Integer>(1, 2, 3));
        assertTrue(list instanceof ArrayList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void createArrayList_varArgs_withNull() {
        List<Integer> list = createArrayList((Integer[]) null);
        assertTrue(list instanceof ArrayList<?>);
    }

    @Test
    public void createArrayList_varArgs() {
        List<Integer> list = createArrayList(1, 2, 3);
        assertTrue(list instanceof ArrayList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void createLinkedList_noArgs() {
        LinkedList<Integer> list = createLinkedList();
        assertTrue(list instanceof LinkedList<?>);
    }

    @Test
    public void createLinkedList_iterable() {
        // colleciton
        LinkedList<Integer> list = createLinkedList(Arrays.asList(1, 2, 3));
        assertTrue(list instanceof LinkedList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());

        // iterable
        list = createLinkedList(new SimpleIterable<Integer>(1, 2, 3));
        assertTrue(list instanceof LinkedList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void createLinkedList_varArgs_withNull() {
        List<Integer> list = createLinkedList((Integer[]) null);
        assertTrue(list instanceof LinkedList<?>);
    }

    @Test
    public void createLinkedList_varArgs() {
        List<Integer> list = createLinkedList(1, 2, 3);
        assertTrue(list instanceof LinkedList<?>);
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void asList_() {
        assertTrue(asList((Object[]) null).isEmpty());

        String[] s = new String[] { "a", "b", "c" };
        List<String> list = asList(s);

        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));

        s[0] = "aa";
        assertEquals("aa", list.get(0));
    }

    @Test
    public void createHashMap_noArgs() {
        Map<String, Integer> map = createHashMap();
        assertTrue(map instanceof HashMap<?, ?>);
    }

    @Test
    public void createHashMap_capacity() {
        Map<String, Integer> map = createHashMap(2);
        assertSame(HashMap.class, map.getClass());
    }

    @Test
    public void createArrayHashMap_noArgs() {
        Map<String, Integer> map = createArrayHashMap();
        assertTrue(map instanceof ArrayHashMap<?, ?>);
    }

    @Test
    public void createArrayHashMap_capacity() {
        Map<String, Integer> map = createArrayHashMap(2);
        assertTrue(map instanceof ArrayHashMap<?, ?>);

        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 1);
        map.put("d", 1);
        map.put("e", 1);

        assertArrayEquals(new Object[] { "a", "b", "c", "d", "e" }, map.keySet().toArray());
    }

    @Test
    public void createLinkedHashMap_noArgs() {
        Map<String, Integer> map = createLinkedHashMap();
        assertTrue(map instanceof LinkedHashMap<?, ?>);
    }

    @Test
    public void createLinkedHashMap_capacity() {
        Map<String, Integer> map = createLinkedHashMap(2);
        assertTrue(map instanceof LinkedHashMap<?, ?>);

        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 1);
        map.put("d", 1);
        map.put("e", 1);

        assertArrayEquals(new Object[] { "a", "b", "c", "d", "e" }, map.keySet().toArray());
    }

    @Test
    public void createTreeMap_noArgs() {
        Map<String, Integer> map = createTreeMap();
        assertTrue(map instanceof TreeMap<?, ?>);

        map.put("e", 1);
        map.put("d", 1);
        map.put("c", 1);
        map.put("b", 1);
        map.put("a", 1);

        assertArrayEquals(new Object[] { "a", "b", "c", "d", "e" }, map.keySet().toArray());
    }

    @Test
    public void createTreeMap_comparator() {
        Map<String, Integer> map = createTreeMap(new DescendentComparator<String>());
        assertTrue(map instanceof TreeMap<?, ?>);

        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 1);
        map.put("d", 1);
        map.put("e", 1);

        assertArrayEquals(new Object[] { "e", "d", "c", "b", "a" }, map.keySet().toArray());
    }

    @Test
    public void createConcurrentHashMap_noArgs() {
        ConcurrentMap<String, Integer> cmap = createConcurrentMap();
        assertNotNull(cmap);
    }

    @Test
    public void createHashSet_noArgs() {
        Set<Integer> set = createHashSet();
        assertTrue(set instanceof HashSet<?>);
    }

    @Test
    public void createHashSet_varArgs_withNull() {
        Set<Integer> set = createHashSet((Integer[]) null);
        assertTrue(set instanceof HashSet<?>);
    }

    @Test
    public void createHashSet_varArgs() {
        Set<Integer> set = createHashSet(11, 22, 33, 44, 55, 66, 77, 88, 99, 100);
        assertTrue(set instanceof HashSet<?>);

        Object[] elems = set.toArray();

        assertArrayNotEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);

        Arrays.sort(elems);

        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);
    }

    @Test
    public void createHashSet_iterable() {
        // collection
        Set<Integer> set = createHashSet(Arrays.asList(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof HashSet<?>);
        Object[] elems = set.toArray();
        assertArrayNotEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);
        Arrays.sort(elems);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);

        // iterable
        set = createHashSet(new SimpleIterable<Integer>(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof HashSet<?>);
        elems = set.toArray();
        assertArrayNotEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);
        Arrays.sort(elems);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, elems);
    }

    @Test
    public void createArrayHashSet_noArgs() {
        Set<Integer> set = createArrayHashSet();
        assertTrue(set instanceof ArrayHashSet<?>);
    }

    @Test
    public void createArrayHashSet_varArgs_withNull() {
        Set<Integer> set = createArrayHashSet((Integer[]) null);
        assertTrue(set instanceof ArrayHashSet<?>);
    }

    @Test
    public void createArrayHashSet_varArgs() {
        Set<Integer> set = createArrayHashSet(11, 22, 33, 44, 55, 66, 77, 88, 99, 100);
        assertTrue(set instanceof ArrayHashSet<?>);

        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createArrayHashSet_iterable() {
        // collection
        Set<Integer> set = createArrayHashSet(Arrays.asList(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof ArrayHashSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());

        // iterable
        set = createArrayHashSet(new SimpleIterable<Integer>(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof ArrayHashSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createLinkedHashSet_noArgs() {
        Set<Integer> set = createLinkedHashSet();
        assertTrue(set instanceof LinkedHashSet<?>);
    }

    @Test
    public void createLinkedHashSet_varArgs_withNull() {
        Set<Integer> set = createLinkedHashSet((Integer[]) null);
        assertTrue(set instanceof LinkedHashSet<?>);
    }

    @Test
    public void createLinkedHashSet_varArgs() {
        Set<Integer> set = createLinkedHashSet(11, 22, 33, 44, 55, 66, 77, 88, 99, 100);
        assertTrue(set instanceof LinkedHashSet<?>);

        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createLinkedHashSet_iterable() {
        // collection
        Set<Integer> set = createLinkedHashSet(Arrays.asList(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof LinkedHashSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());

        // iterable
        set = createLinkedHashSet(new SimpleIterable<Integer>(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof LinkedHashSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createTreeSet_noArgs() {
        Set<Integer> set = createTreeSet();
        assertTrue(set instanceof TreeSet<?>);
    }

    @Test
    public void createTreeSet_comparator() {
        Set<Integer> set = createTreeSet(new DescendentComparator<Integer>());
        assertTrue(set instanceof TreeSet<?>);

        set.add(11);
        set.add(22);
        set.add(33);
        set.add(44);
        set.add(55);
        set.add(66);
        set.add(77);
        set.add(88);
        set.add(99);
        set.add(100);

        assertArrayEquals(new Object[] { 100, 99, 88, 77, 66, 55, 44, 33, 22, 11 }, set.toArray());
    }

    @Test
    public void createTreeSet_varArgs_withNull() {
        Set<Integer> set = createTreeSet((Integer[]) null);
        assertTrue(set instanceof TreeSet<?>);
    }

    @Test
    public void createTreeSet_varArgs() {
        Set<Integer> set = createTreeSet(100, 99, 88, 77, 66, 55, 44, 33, 22, 11);
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createTreeSet_varArgs_comparator() {
        Set<Integer> set = createTreeSet(new DescendentComparator<Integer>(), 11, 22, 33, 44, 55, 66, 77, 88, 99, 100);
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 100, 99, 88, 77, 66, 55, 44, 33, 22, 11 }, set.toArray());
    }

    @Test
    public void createTreeSet_iterable() {
        // collection
        Set<Integer> set = createTreeSet(Arrays.asList(100, 99, 88, 77, 66, 55, 44, 33, 22, 11));
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());

        // iterable
        set = createTreeSet(new SimpleIterable<Integer>(100, 99, 88, 77, 66, 55, 44, 33, 22, 11));
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 100 }, set.toArray());
    }

    @Test
    public void createTreeSet_iterable_comparator() {
        // collection
        Set<Integer> set =
                createTreeSet(new DescendentComparator<Integer>(),
                        Arrays.asList(11, 22, 33, 44, 55, 66, 77, 88, 99, 100));
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 100, 99, 88, 77, 66, 55, 44, 33, 22, 11 }, set.toArray());

        // iterable
        set =
                createTreeSet(new DescendentComparator<Integer>(), new SimpleIterable<Integer>(11, 22, 33, 44, 55, 66,
                        77, 88, 99, 100));
        assertTrue(set instanceof TreeSet<?>);
        assertArrayEquals(new Object[] { 100, 99, 88, 77, 66, 55, 44, 33, 22, 11 }, set.toArray());
    }

    private void assertArrayNotEquals(Object[] expectedNot, Object[] result) {
        boolean failed = false;

        try {
            assertArrayEquals(expectedNot, result);
            failed = true;
        } catch (AssertionError e) {
        }

        if (failed) {
            fail();
        }
    }

    private static class SimpleIterable<T> implements Iterable<T> {
        private final List<T> list;

        public SimpleIterable(T...a) {
            list = new ArrayList<T>(Arrays.asList(a));
        }

        public Iterator<T> iterator() {
            return list.iterator();
        }

        @Override
        public String toString() {
            return list.toString();
        }
    }

    private static class DescendentComparator<T extends Comparable<? super T>> implements Comparator<T> {
        public int compare(T o1, T o2) {
            return o2.compareTo(o1);
        }
    }
}
