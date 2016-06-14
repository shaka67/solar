/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns.dynamic;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RnsDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RnsDynamicException() {
        super();
    }

    public RnsDynamicException(String msg) {
        super(msg);
    }

    public RnsDynamicException(Throwable t) {
        super(t);
    }

    public RnsDynamicException(String msg, Throwable t) {
        super(msg, t);
    }
}
