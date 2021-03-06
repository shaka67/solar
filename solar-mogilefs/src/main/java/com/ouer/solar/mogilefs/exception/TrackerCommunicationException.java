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

public class TrackerCommunicationException extends MogileException {

	private static final long serialVersionUID = 1L;

	public TrackerCommunicationException(String message) {
		super(message);
	}

	public TrackerCommunicationException(String message, Throwable t) {
		super(message, t);
	}

	@Override
	public ResultCode getResultCode() {
		return MogileResult.TRACKER_COMMUNICATION;
	}
}
