/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IbsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IbsException() {
        super();
    }

    public IbsException(String msg) {
        super(msg);
    }

    public IbsException(Throwable t) {
        super(t);
    }

    public IbsException(String msg, Throwable t) {
        super(msg, t);
    }

}
