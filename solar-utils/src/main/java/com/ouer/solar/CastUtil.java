/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ouer.solar.collection.ListMap;

/**
 * Cast Util
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-12 上午9:59:59
 */
public abstract class CastUtil { 

    public static <O> O cast(Object object) {
        @SuppressWarnings("unchecked")
        O result = (O) object;

        return result;
    }

    public static <T> String[] cast(T...ts) {
        if (ArrayUtil.isEmpty(ts)) {
            return null;
        }

        String[] result = new String[ts.length];
        for (int i = 0, size = ts.length; i < size; i++) {
            result[i] = String.valueOf(ts[i]);
        }

        return result;
    }

    public static <T> String[] cast(Collection<T> ts) {
        if (CollectionUtil.isEmpty(ts)) {
            return null;
        }

        String[] result = new String[ts.size()];

        int i = 0;
        for (Iterator<T> iter = ts.iterator(); iter.hasNext(); i++) {
            T next = iter.next();
            result[i] = String.valueOf(next);
        }

        return result;
    }

    public static <O> List<O> cast(List<Object> values) {
        if (CollectionUtil.isEmpty(values)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<O> result = (List<O>) values;
        return result;
    }

    public static <O> List<O> castWildcard(List<?> values) {
        if (CollectionUtil.isEmpty(values)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<O> result = (List<O>) values;
        return result;
    }

    public static <K, V> Map<K, V> castMap(Map<?, ?> map) {
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Map<K, V> result = (Map<K, V>) map;
        return result;
    }

    public static <K, V> ListMap<K, V> castListMap(ListMap<?, ?> map) {
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        ListMap<K, V> result = (ListMap<K, V>) map;
        return result;
    }
}
