package com.ouer.solar.remote;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation used to indicate if an implementation class is a remote worker.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Documented
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface RemoteWorker {

    /**
     * (Optional) The group name of the annotated remote worker.
     *
     * @return The group name of the declared remote worker.
     */
    String group() default "DEFAULT";
}