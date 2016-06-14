/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 源对象拷贝字段
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月9日 下午2:03:09
 */
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyField {
    Class<?>[] supportFor() default Object.class;
}
