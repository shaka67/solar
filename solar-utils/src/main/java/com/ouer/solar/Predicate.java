/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Predicate<T> {

    /**
     * Applies this predicate to the given object.
     *
     * @param source The input/source element that the predicate should operate on
     * @return The value of this predicate when applied to the input {@code T}
     */
    boolean apply(T source);
}
