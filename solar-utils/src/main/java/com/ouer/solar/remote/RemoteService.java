package com.ouer.solar.remote;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation used to indicate if an interface is a remote service.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Documented
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface RemoteService {

    /**
     * (Optional) The group name of the annotated remote service.
     *
     * @return The group name of the declared remote service.
     */
    String group() default "DEFAULT";
}