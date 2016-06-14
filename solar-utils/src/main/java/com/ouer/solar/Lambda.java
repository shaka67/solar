/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Lambda<E, T> {

    /**
     * Applies the function to an object of type {@code E}, resulting in an object
     * of type {@code T}. The base object serves as an accumulator.
     * Note that types {@code E} and {@code T} may or may not
     * be the same.
     *
     * @param source The source object
     * @param base The base accumulator
     * @return The result object
     */
    T apply(E source, T base);
}
