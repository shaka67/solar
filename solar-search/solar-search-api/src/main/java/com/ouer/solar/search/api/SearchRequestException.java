/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    public SearchRequestException() {
        super();
    }

    public SearchRequestException(String msg) {
        super(msg);
    }

    public SearchRequestException(Throwable t) {
        super(t);
    }

    public SearchRequestException(String msg, Throwable t) {
        super(msg, t);
    }

}