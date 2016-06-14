/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * Since {@link Cloneable} is just a marker interface, it is not possible to clone different type of objects at once.
 * This interface helps for user objects, but, obviously, it can't change JDK classes.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:28:25
 * @param <T>
 */
public interface CloneableObject<T> extends Cloneable {

    /**
     * Performs instance cloning.
     * 
     * @see Object#clone()
     */
    T clone() throws CloneNotSupportedException;

}
