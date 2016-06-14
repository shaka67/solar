/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;



/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月4日 下午5:27:39
 */

public enum CopyOfValveTag implements ResultCode {

	CREATE_REPORT, REPORT_FILE_RECORD, CUSTOM_REPORT_VIEW,
	STANDARD_REPORT_VIEW, STANDARD_GROUP_VIEW, STANDARD_PLAN_VIEW,
	STANDARD_ACCOUNT_VIEW, CSV_IMPORT, SEND_DOWNLOAD_MAIL,
	SEND_REMOVED_MAIL, UPDATE_RECORD, PIPELINE_FAIL ;

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
	public ResultCodeMessage getMessage(Object... args) {
		return util.getMessage(args);
	}

	@Override
	public int getCode() {
		return util.getCode();
	}


	public static void main(String[] args) {
		for(final CopyOfValveTag vt : CopyOfValveTag.values()) {
			System.out.println(vt.getCode());
		}
	}

}
