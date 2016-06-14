/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.common;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TransportDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TransportDynamicException() {
        super();
    }

    public TransportDynamicException(String message) {
        super(message);
    }

    public TransportDynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransportDynamicException(Throwable cause) {
        super(cause);
    }

}
