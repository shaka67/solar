/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.dynamic;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogilefsDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MogilefsDynamicException() {
        super();
    }

    public MogilefsDynamicException(String message) {
        super(message);
    }

    public MogilefsDynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public MogilefsDynamicException(Throwable cause) {
        super(cause);
    }

}
