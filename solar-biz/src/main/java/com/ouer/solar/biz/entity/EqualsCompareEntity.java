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
 * 使用<code>EqualsCompare</code>注解的实体
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-6 下午3:43:06
 */

public class EqualsCompareEntity<T extends EqualsCompareEntity<T>> extends Entity<T> {

    /**
     * 
     */
    private static final long serialVersionUID = 4912602716214196795L;

    private final FieldCache cache = FieldCache.getInstance();

    @Override
    protected boolean isEquals(T obj) {
        // 比较每个字段是否相等，如果所有字段都相等，则返回true，否则返回false
        Field[] fields = cache.getFields(this.getClass(), EqualsCompare.class);

        for (Field field : fields) {
            Object thisValue = ReflectionUtil.readField(field, this);
            Object thatValue = ReflectionUtil.readField(field, obj);
            if (!ObjectUtil.isEquals(thisValue, thatValue)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected Object hashKey() {
        int key = 0;
        // 取所有字段hashCode总和作为hashKey
        Field[] fields = cache.getFields(this.getClass(), EqualsCompare.class);

        for (Field field : fields) {
            Object value = ReflectionUtil.readField(field, this);
            if (value != null) {
                key += value.hashCode();
            }
        }

        return key;
    }

}
