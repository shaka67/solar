/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RetryPolicy {

    /**
     * @param context the current retry context
     * @return true if the operation can proceed
     */
    public boolean canRetry(RetryContext context);

    public int getRetryInterval();

    /**
     * Open a new retry context needed for the retry operation.
     * @return A new retry context object associated with this retry policy.
     */
    public RetryContext open();

    /**
     * Close the retry context associated with this retry policy.
     */
    public void close();
}