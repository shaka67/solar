/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;


/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月4日 下午5:27:39
 */

public enum ValveTag implements ResultCode {

	CREATE_REPORT,
	REPORT_FILE_RECORD,
	DYNAMIC_PARAM;

//	public boolean isErrorHandler() {
//		return false;
//	}

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
	public ResultCodeMessage getMessage(final Object...args) {
		return util.getMessage(args);
	}

	@Override
	public int getCode() {
		return util.getCode();
	}


	public static void main(String[] args) {
		for(final ValveTag vt : ValveTag.values()) {
			System.out.println(vt.getMessage().getMessage());
		}
		System.out.println(DYNAMIC_PARAM.getMessage("fuck", "you"));
	}

}
