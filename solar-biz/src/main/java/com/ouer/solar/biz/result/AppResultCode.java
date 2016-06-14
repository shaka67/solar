/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;


/**
 * export FIXME removed
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月13日 下午8:18:00
 */

public enum AppResultCode implements ResultCode {

    /** 执行成功。 */
    SUCCESS,

    /** 参数不合法。 */
    INVALID_PARAM,

    /** 执行数据访问操作失败。 */
    DAO_FAILURE,

    /** 执行服务操作失败。 */
    SERVICE_FAILURE,

    /** 执行业务逻辑操作失败。 */
    BIZ_FAILURE,

    /** 重新尝试 。 */
    REDO,

    /** 重试次数上限 。 */
    LIMIT_OF_RETRY,

    /** 执行出错。 */
    ERROR;

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
