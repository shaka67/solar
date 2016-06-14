/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.enums;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.biz.enums.Enum.EnumType;
import com.ouer.solar.biz.enums.internal.EnumConstant;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;
import com.ouer.solar.source.Check;

/**
 * 提供获取自定义枚举的工具类
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午2:51:05
 */

public class EnumUtil {
    private static final Logger logger = LoggerFactory.getLogger(EnumUtil.class);

    private static final Map<ClassLoader, Map<String, EnumType>> entries = CollectionUtil.createWeakHashMap();

    /**
     * 取得<code>Enum</code>值的类型.
     * 
     * @param enumClass 枚举类型
     * 
     * @return <code>Enum</code>值的类型
     */
    public static Class<?> getUnderlyingClass(Class<?> enumClass) {
        return getEnumType(enumClass).getUnderlyingClass();
    }

    /**
     * 判断指定名称的枚举量是否被定义.
     * 
     * @param enumClass 枚举类型
     * @param name 枚举量的名称
     * 
     * @return 如果存在, 则返回<code>true</code>
     */
    public static boolean isNameDefined(Class<?> enumClass, String name) {
        return getEnumType(enumClass).nameMap.containsKey(name);
    }

    /**
     * 判断指定值的枚举量是否被定义.
     * 
     * @param enumClass 枚举类型
     * @param value 枚举量的值
     * 
     * @return 如果存在, 则返回<code>true</code>
     */
    public static boolean isValueDefined(Class<?> enumClass, Number value) {
        return getEnumType(enumClass).valueMap.containsKey(value);
    }

    /**
     * 取得指定名称的枚举量.
     * 
     * @param enumClass 枚举类型
     * @param name 枚举量的名称
     * 
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByName(Class<?> enumClass, String name) {
        EnumType enumType = getEnumType(enumClass);

        if (enumType.enumList.size() != enumType.nameMap.size()) {
            enumType.populateNames(enumClass);
        }

        return enumType.nameMap.get(name);
    }

    /**
     * 取得指定值的枚举量.
     * 
     * @param enumClass 枚举类型
     * @param value 枚举量的值
     * 
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class<?> enumClass, Number value) {
        return getEnumType(enumClass).valueMap.get(value);
    }

    /**
     * 取得指定值的枚举量.
     * 
     * @param enumClass 枚举类型
     * @param value 枚举量的值
     * 
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class<?> enumClass, int value) {
        return getEnumType(enumClass).valueMap.get(Integer.valueOf(value));
    }

    /**
     * 取得指定值的枚举量.
     * 
     * @param enumClass 枚举类型
     * @param value 枚举量的值
     * 
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class<?> enumClass, long value) {
        return getEnumType(enumClass).valueMap.get(Long.valueOf(value));
    }

    /**
     * 取得指定类型的所有枚举量的<code>Map</code>, 此<code>Map</code>是有序的.
     * 
     * @param enumClass 枚举类型
     * 
     * @return 指定类型的所有枚举量的<code>Map</code>
     */
    public static Map<?, ?> getEnumMap(Class<?> enumClass) {
        return Collections.unmodifiableMap(getEnumType(enumClass).nameMap);
    }

    /**
     * 取得指定类型的所有枚举量的<code>Iterator</code>.
     * 
     * @param enumClass 枚举类型
     * 
     * @return 指定类型的所有枚举量的<code>Iterator</code>
     */
    public static Iterator<?> getEnumIterator(Class<?> enumClass) {
        return getEnumType(enumClass).enumList.iterator();
    }

    /**
     * 取得指定类的<code>ClassLoader</code>对应的entry表.
     * 
     * @param enumClass <code>Enum</code>类
     * 
     * @return entry表
     */
    static Map<String, EnumType> getEnumEntryMap(Class<?> enumClass) {
        ClassLoader classLoader = enumClass.getClassLoader();
        Map<String, EnumType> entryMap = null;

        synchronized (entries) {
            entryMap = entries.get(classLoader);

            if (entryMap == null) {
                entryMap = CollectionUtil.createHashMap();
                entries.put(classLoader, entryMap);
            }
        }

        return entryMap;
    }

    /**
     * 取得<code>Enum</code>类的<code>EnumType</code>
     * 
     * @param enumClass <code>Enum</code>类
     * 
     * @return <code>Enum</code>类对应的<code>EnumType</code>对象
     */
    static EnumType getEnumType(Class<?> enumClass) {
        if (enumClass == null) {
            throw new NullPointerException(EnumConstant.ENUM_CLASS_IS_NULL);
        }

        synchronized (enumClass) {
            if (!Enum.class.isAssignableFrom(enumClass)) {
                throw new IllegalArgumentException(MessageFormat.format(EnumConstant.CLASS_IS_NOT_ENUM,
                        new Object[] { enumClass.getName() }));
            }

            Map<String, EnumType> entryMap = getEnumEntryMap(enumClass);
            EnumType enumType = entryMap.get(enumClass.getName());

            if (enumType == null) {
                Method createEnumTypeMethod =
                        findStaticMethod(enumClass, EnumConstant.CREATE_ENUM_TYPE_METHOD_NAME, new Class[0]);

                if (createEnumTypeMethod != null) {
                    try {
                        enumType = (EnumType) createEnumTypeMethod.invoke(null, new Object[0]);
                    } catch (Exception e) {
                        logger.error("Exception error", e);
                    }
                }

                if (enumType != null) {
                    entryMap.put(enumClass.getName(), enumType);

                    // 在JDK5下面，class loader完成并不意味着所有的常量被装配
                    // 下面的代码强制装配常量。
                    enumType.populateNames(enumClass);
                }
            }

            if (enumType == null) {
                throw new UnsupportedOperationException(MessageFormat.format(EnumConstant.FAILED_CREATING_ENUM_TYPE,
                        new Object[] { enumClass.getName() }));
            }

            return enumType;
        }
    }

    /**
     * 查找方法.
     * 
     * @param enumClass 枚举类型
     * @param methodName 方法名
     * @param paramTypes 参数类型表
     * 
     * @return 方法对象, 或<code>null</code>表示未找到
     */
    @Check
    private static Method findStaticMethod(Class<?> enumClass, String methodName, Class<?>[] paramTypes) {
        Method method = null;

        for (Class<?> clazz = enumClass; !clazz.equals(Enum.class); clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, paramTypes);
                break;
            } catch (NoSuchMethodException e) {
                // FIXME
                // logger.error("NoSuchMethodException error", e);
            }
        }

        if ((method != null) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }

        return null;
    }
}
