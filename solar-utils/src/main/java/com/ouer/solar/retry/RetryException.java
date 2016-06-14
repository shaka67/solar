/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RetryException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public RetryException(String message) {
        super(message);
    }

    public RetryException(Throwable cause) {
        super(cause);
    }

    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }

}