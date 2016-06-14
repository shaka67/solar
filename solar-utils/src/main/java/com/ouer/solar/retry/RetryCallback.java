/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 * Provide callback mechanism for retry operation intercetor.
 *
 * @param <T>
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RetryCallback<T> {

    /**
     * Execute an operation with retry semantics. Operations should generally be
     * idempotent, but implementations may choose to implement compensation
     * semantics when an operation is retried.
     *
     * @param context the current retry context.
     * @return the result of the successful operation.
     * @throws Exception if processing fails
     */
    public T apply(RetryContext context) throws Throwable;

}