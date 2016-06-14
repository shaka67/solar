/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RetryContext {

    private int count;
    private boolean terminated;
    
    public int getRetryCount() {
        return count;
    }

    public void incrementRetryCount() {
        count++;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

}