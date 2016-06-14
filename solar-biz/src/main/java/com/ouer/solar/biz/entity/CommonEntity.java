/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.lang.reflect.Field;

import com.ouer.solar.ObjectUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.cache.FieldCache;

/**
 * 一个通用的抽象视图类
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-9 下午5:47:35
 */

public class CommonEntity<T extends CommonEntity<T>> extends Entity<T> {

    /**
     * 
     */
    private static final long serialVersionUID = -4536848994064975165L;

    private static final FieldCache cache = FieldCache.getInstance();

    @Override
    protected boolean isEquals(T obj) {
        // 比较每个字段是否相等，如果所有字段都相等，则返回true，否则返回false
        Field[] fields = cache.getInstanceFields(this.getClass());
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
        Field[] fields = cache.getInstanceFields(this.getClass());
        for (Field field : fields) {
            Object value = ReflectionUtil.readField(field, this);
            if (value != null) {
                key += value.hashCode();
            }
        }

        return key;
    }

}
