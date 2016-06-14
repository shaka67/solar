/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.exception;

import com.ouer.solar.biz.result.ResultCode;
import com.ouer.solar.biz.result.ResultMessageException;
import com.ouer.solar.mogilefs.MogileResult;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogileException extends ResultMessageException {

	private static final long serialVersionUID = 1L;

	/**
     *
     */
	public MogileException() {
		super();
	}

	/**
	 * @param message
	 */
	public MogileException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MogileException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public MogileException(Throwable cause) {
		super(cause);
	}

	@Override
	public ResultCode getResultCode() {
		return MogileResult.MOGILE_ERROR;
	}

}
