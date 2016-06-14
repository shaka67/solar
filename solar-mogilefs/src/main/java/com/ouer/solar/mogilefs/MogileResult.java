/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import com.ouer.solar.biz.result.ResultCode;
import com.ouer.solar.biz.result.ResultCodeMessage;
import com.ouer.solar.biz.result.ResultCodeUtil;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum MogileResult implements ResultCode {

	SUCCESS,
	INVALID_PARAM,
	TIME_OUT,
	NO_SERVER,
	NOT_FOUND,
	NO_TRACKERS,
	MOGILE_ERROR,
	STORAGE_COMMUNICATION,
	TRACKER_COMMUNICATION,
	BAD_HOST_FORMAT;

	private final ResultCodeUtil util = new ResultCodeUtil(this);

	@Override
	public String getName() {
		return util.getName();
	}

	@Override
	public ResultCodeMessage getMessage() {
		return util.getMessage();
	}

	@Override
	public ResultCodeMessage getMessage(Object... args) {
		return util.getMessage(args);
	}

	@Override
	public int getCode() {
		return util.getCode();
	}

}
