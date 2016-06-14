/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.lang.reflect.Field;

import com.ouer.solar.ObjectUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.biz.entity.annotation.EqualsCompare;
import com.ouer.solar.cache.FieldCache;

/**
 * 提取对象比较的通用方法
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-6 下午3:52:04
 */

public abstract class EqualsUtil {

    private static final FieldCache cache = FieldCache.getInstance();

    /**
     * 比较两个对象是是否“相等”，如果相等则返回<code>true</code>
     * <p>
     * 当两个对象都为<code>null</code>，则返回<code>true</code> <br>
     * 否则则比较对象的<code>class</code>类型和所有“属性”是否均相等
     * 
     * @param one 比较对象之一
     * @param another 比较对象之一
     * @return 是否“相等”，如果相等则返回<code>true</code>
     */
    public static boolean equals(Object one, Object another) {
        return equals(one, another, false);
    }

    /**
     * 比较两个对象是是否“相等”，如果相等则返回<code>true</code>
     * <p>
     * 当两个对象都为<code>null</code>，则返回<code>true</code> <br>
     * 否则则比较对象的<code>class</code>类型和所有“属性”是否均相等
     * 
     * @param one 比较对象之一
     * @param another 比较对象之一
     * @param useAnnotation 是否使用比较注解
     * @return 是否“相等”，如果相等则返回<code>true</code>
     */
    public static boolean equals(Object one, Object another, boolean useAnnotation) {
        if (one == another) {
            return true;
        }

        if (another == null || one.getClass() != another.getClass()) {
            return false;
        }

        // 比较每个字段是否相等，如果所有字段都相等，则返回true，否则返回false
        Field[] fields =
                (useAnnotation) ? cache.getFields(one.getClass(), EqualsCompare.class) : cache.getInstanceFields(one
                        .getClass());

        for (Field field : fields) {
            Object thisValue = ReflectionUtil.readField(field, one);
            Object thatValue = ReflectionUtil.readField(field, another);
            if (!ObjectUtil.isEquals(thisValue, thatValue)) {
                return false;
            }
        }

        return true;
    }

}
