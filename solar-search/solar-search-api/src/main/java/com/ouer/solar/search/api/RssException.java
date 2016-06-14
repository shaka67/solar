/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RssException() {
        super();
    }

    public RssException(String msg) {
        super(msg);
    }

    public RssException(Throwable t) {
        super(t);
    }

    public RssException(String msg, Throwable t) {
        super(msg, t);
    }

}