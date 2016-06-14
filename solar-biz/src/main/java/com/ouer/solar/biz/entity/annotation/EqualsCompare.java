/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 继承 {@link com.ouer.solar.biz.entity.Entity} 的对象，如果<code>equals</code>方法需要比较某个<code>Field</code>， 用此注解表示，该注释可继承
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-6 下午3:37:54
 */

// 可继承
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface EqualsCompare {

}
