/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.exception;

import com.ouer.solar.biz.result.ResultCode;
import com.ouer.solar.mogilefs.MogileResult;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class NoTrackersException extends MogileException {

	private static final long serialVersionUID = 1L;

	/**
     *
     */
	public NoTrackersException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoTrackersException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoTrackersException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public NoTrackersException(Throwable cause) {
		super(cause);
	}

	@Override
	public ResultCode getResultCode() {
		return MogileResult.NO_TRACKERS;
	}

}
