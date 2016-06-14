/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleRetryPolicy implements RetryPolicy {
	
    private static final int DEFAULT_MAXIMUM_ATTEMPTS = 3;
    private static final int DEFAULT_RETRY_INTERVAL = 1000; // 1 seconds
    
    private final int attempts; // attempt count
    private final int interval; // retry interval in millseconds
    
    public SimpleRetryPolicy() {
        this(DEFAULT_MAXIMUM_ATTEMPTS, DEFAULT_RETRY_INTERVAL);
    }

    public SimpleRetryPolicy(int attempts, int interval) {
        this.attempts = attempts;
        this.interval = interval;
    }

    public int getMaximumAttempts() {
        return attempts;
    }

    /**
     * @return true if the number of attempts so far is less than the limit.
     */
    @Override
    public boolean canRetry(RetryContext context) {
        return context.getRetryCount() < getMaximumAttempts();
    }

    @Override
    public int getRetryInterval() {
        return interval;
    }

    @Override
    public RetryContext open() {
        return new RetryContext();
    }

    @Override
    public void close() {
        // NO-OP
    }

}