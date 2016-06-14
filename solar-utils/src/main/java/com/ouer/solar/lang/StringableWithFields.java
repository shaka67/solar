/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.able.ToStringable;
import com.ouer.solar.cache.FieldCache;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-3 上午1:13:30
 */
public class StringableWithFields implements ToStringable {

    /**
     * 表示打印对象时需要显示的 {@link Field java.lang.reflect.Field}
     * 
     * @author <a href="indra@ixiaopu.com">chenxi</a>
     * @version create on 2014-12-3 上午12:59:33
     */
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    public static @interface ShowField {

    }

    private static final FieldCache fieldCache = FieldCache.getInstance();

    @Override
    public String toString() {

        Field[] fields = fieldCache.getFields(this.getClass(), ShowField.class);

        return ReflectionUtil.dump(this, fields);

    }

}
