package com.ouer.solar.remote;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation used to indicate if declared method is a remote method.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface RemoteMethod {

    /**
     * (Optional) The name of the remote method. Defaults to the method name.
     */
    String name() default "";
}