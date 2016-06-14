/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

import java.util.NoSuchElementException;

/**
 * 迭代器
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-6-17 上午12:40:50
 */
public interface Iteration<E> {
    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other words, returns <tt>true</tt> if <tt>next</tt>
     * would return an element rather than throwing an exception.)
     * 
     * @return <tt>true</tt> if the iterator has more elements.
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @exception NoSuchElementException iteration has no more elements.
     */
    E next();

    /**
     * 重置
     */
    void reset();
}
