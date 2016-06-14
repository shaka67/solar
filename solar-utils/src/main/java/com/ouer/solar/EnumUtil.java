/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.concurrent.Callable;

import com.ouer.solar.able.Computable;
import com.ouer.solar.able.Valuable;
import com.ouer.solar.cache.ConcurrentCache;

/**
 * Enum Util
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 上午12:46:30
 */
public abstract class EnumUtil {

    private static final Computable<String, Enum<?>> COMPUTABLE = ConcurrentCache.createComputable();

    private static final Computable<Class<Enum<?>>, Integer> ENUM_TOTAL = ConcurrentCache.createComputable();

    public static <E extends Enum<E>> E parseName(Class<E> enumType, String name) {
        if (enumType == null || StringUtil.isBlank(name)) {
            return null;
        }

        return Enum.valueOf(enumType, name);
    }

    public static <E extends Enum<E>> int total(final Class<E> enumType) {
        if (enumType == null) {
            return -1;
        }

        @SuppressWarnings({ "unchecked" })
        Class<Enum<?>> enumClass = (Class<Enum<?>>) enumType;
        return ENUM_TOTAL.get(enumClass, new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                E[] enums = ReflectionUtil.invokeStaticMethod(enumType, "values");
                return enums.length;
            }
        });

    }

    public static <E extends Enum<E>> E find(final Class<E> enumType, final Object value) {
        if (ObjectUtil.isAnyNull(enumType, value)) {
            return null;
        }

        if (!ClassUtil.isInterfaceImpl(enumType, Valuable.class)) {
            return null;
        }

        String key = enumType + ":(" + value.getClass() + ":" + value + ")";
        @SuppressWarnings("unchecked")
        E result = (E) COMPUTABLE.get(key, new Callable<Enum<?>>() {

            @Override
            public Enum<?> call() throws Exception {
                Valuable<?>[] values = ReflectionUtil.invokeStaticMethod(enumType, "values");

                for (Valuable<?> e : values) {
                    if (value.equals(e.value())) {
                        @SuppressWarnings("unchecked")
                        E result = (E) e;
                        return result;
                    }
                }

                return null;
            }
        });

        return result;

    }
}
