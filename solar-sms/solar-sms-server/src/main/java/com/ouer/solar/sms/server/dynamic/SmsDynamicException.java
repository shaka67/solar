/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.dynamic;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SmsDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SmsDynamicException() {
        super();
    }

    public SmsDynamicException(String message) {
        super(message);
    }

    public SmsDynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsDynamicException(Throwable cause) {
        super(cause);
    }
}
