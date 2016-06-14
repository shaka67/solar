/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Target({FIELD})
@Retention(RUNTIME)
public @interface TemplateVariable {
    public String variable();
}
