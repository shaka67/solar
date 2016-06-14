/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractResultCode implements ResultCode {

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
	public int getCode() {
		return util.getCode();
	}

	@Override
	public String toString() {
		return util.getMessage().getMessage();
	}
}
