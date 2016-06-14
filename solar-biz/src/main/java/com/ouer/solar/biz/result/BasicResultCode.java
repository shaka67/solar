/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

/**
 * 常用处理结果的代码
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午4:13:40
 */

public enum BasicResultCode implements ResultCode {

    /** 成功执行命令。 */
    SUCCESS,

    /** 参数不合法。 */
    INVALID_PARAM,

    /**
     * 表示抛出未预料到异常，或者<code>isSuccess()</code>为<code>false</code>却未指明具体的 <code>ResultCode</code>。
     */
    GENERIC_FAILURE,

    /**
     * 表示service抛出未预料到异常，或者<code>isSuccess()</code>为<code>false</code>却未指明具体的 <code>ResultCode</code>。
     */
    SERVICE_FAILURE,

    /**
     * 表示DAO抛出未预料到异常，或者<code>isSuccess()</code>为<code>false</code>却未指明具体的 <code>ResultCode</code>。
     */
    DAO_FAILURE,

    /** 执行业务逻辑操作失败。 */
    BIZ_FAILURE,

    /**
     * 执行命令错误，一般会带有捕获的异常
     */
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
