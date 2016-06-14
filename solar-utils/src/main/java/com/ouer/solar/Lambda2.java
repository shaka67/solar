/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Lambda2<E, T> {

    /**
     * Applies the function to two objects of type {@code E}, resulting in an object
     * of type {@code T}. The base object serves as an accumulator.
     * Note that types {@code E} and {@code T} may or may not
     * be the same.
     *
     * @param source1 The source object from list 1
     * @param source 2 Another source object from list 2
     * @param base The base accumulator
     * @return The result object
     */
    T apply(E source1, E source2, T base);
}
