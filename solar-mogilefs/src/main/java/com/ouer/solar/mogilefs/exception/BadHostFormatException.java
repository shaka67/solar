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

public class BadHostFormatException extends MogileException {

	private static final long serialVersionUID = 1L;

	public BadHostFormatException(String host) {
		super(host);
	}

	@Override
	public ResultCode getResultCode() {
		return MogileResult.BAD_HOST_FORMAT;
	}

}
