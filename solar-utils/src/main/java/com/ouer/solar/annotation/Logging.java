package com.ouer.solar.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation used to indicate if a method needs to be logged with logging interceptor.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface Logging {
}