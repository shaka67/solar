/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DataSourceException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DataSourceException() {
        super();
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }
}
