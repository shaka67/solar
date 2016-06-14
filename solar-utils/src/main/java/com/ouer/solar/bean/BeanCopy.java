/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import com.ouer.solar.ClassUtil;
import com.ouer.solar.ObjectUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.cache.BeanInfoCache;
import com.ouer.solar.cache.FieldCache;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月15日 下午4:09:04
 */
public abstract class BeanCopy {

    private static final FieldCache fieldCache = FieldCache.getInstance();

    private static final BeanInfoCache beanInfoCache = BeanInfoCache.getInstance();

    public static void copy(Object src, Object dest, boolean useAnnotation) {
        if (!useAnnotation) {
            copyProperties(src, dest);
            return;
        }

        copyByAnnotation(src, dest);
    }

    public static void copyProperties(Object src, Object dest) {
        copyProperties(src, dest, false);
    }

    public static void copyProperties(Object src, Object dest, boolean ignoreNull) {
        if (ObjectUtil.isAnyNull(src, dest)) {
            return;
        }

        Class<?> srcClazz = src.getClass();
        Field[] destFields = fieldCache.getInstanceFields(dest.getClass());

        for (Field field : destFields) {
            if (ReflectionUtil.isFinal(field)) {
                continue;
            }
            Field srcField = fieldCache.getInstanceField(srcClazz, field.getName());
            if (srcField != null) {
                Object value = ReflectionUtil.readField(srcField, src);
                if (value != null) {
                    ReflectionUtil.writeField(field, dest, value);
                    continue;
                }
                if (!ignoreNull) {
                    ReflectionUtil.writeField(field, dest, value);
                }
            }

        }
    }

    public static void copyByAnnotation(Object src, Object dest) {
        if (ObjectUtil.isAnyNull(src, dest)) {
            return;
        }

        Class<?> srcClazz = src.getClass();
        Class<?> destClazz = dest.getClass();
        Field[] destFields = fieldCache.getInstanceFields(dest.getClass());

        for (Field field : destFields) {
            if (ReflectionUtil.isFinal(field) || ReflectionUtil.hasAnnotation(field, IgnoreField.class)) {
                continue;
            }
            Field srcField = fieldCache.getField(srcClazz, CopyField.class, field.getName());
            if (srcField != null && supportFor(destClazz, srcField.getAnnotation(CopyField.class))) {
                Object value = ReflectionUtil.readField(srcField, src);
                ReflectionUtil.writeField(field, dest, value);
            }

        }

    }

    public static void copyByMethod(Object src, Object dest) {
        if (ObjectUtil.isAnyNull(src, dest)) {
            return;
        }

        BeanInfo beanInfo = beanInfoCache.getBeanInfo(dest.getClass());

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor destPd : propertyDescriptors) {
            PropertyDescriptor srcPd = beanInfoCache.getPropertyDescriptor(src.getClass(), destPd.getName());
            if (srcPd == null) {
                continue;
            }

            Method readMethod = srcPd.getReadMethod();
            Object value = ReflectionUtil.invokeMethod(readMethod, src);
            Method writeMethod = destPd.getWriteMethod();
            ReflectionUtil.invokeMethod(writeMethod, dest, value);

        }
    }

    private static boolean supportFor(Class<?> fromClass, CopyField copyField) {
        for (Class<?> clazz : copyField.supportFor()) {
            if (ClassUtil.isAssignable(clazz, fromClass)) {
                return true;
            }
        }

        return false;
    }

    public static void map2Object(Object object, Map<?, ?> map) {
        Field[] fields = ReflectionUtil.getAllFieldsOfClass(object.getClass(), true);

        for (Field field : fields) {
            Object value = map.get(field.getName());
            if (value != null) {
                ReflectionUtil.writeField(object, field.getName(), value);
            }
        }
    }

    public static void object2Map(Object object, Map<String, Object> map) {
        Field[] fields = ReflectionUtil.getAllFieldsOfClass(object.getClass(), true);

        for (Field field : fields) {
            Object value = ReflectionUtil.readField(field, object);
            if (value != null) {
                map.put(field.getName(), value);
            }
        }
    }
}
