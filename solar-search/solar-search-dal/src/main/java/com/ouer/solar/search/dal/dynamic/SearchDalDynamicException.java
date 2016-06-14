/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.dynamic;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchDalDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SearchDalDynamicException() {
        super();
    }

    public SearchDalDynamicException(String message) {
        super(message);
    }

    public SearchDalDynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchDalDynamicException(Throwable cause) {
        super(cause);
    }
}
