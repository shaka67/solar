/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

/**
 * 携带<code>ResultCode</code>的异常。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午6:04:47
 */

public class ResultCodeRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 3832907658473910577L;
    private ResultCode resultCode;

    /**
     * 构造一个空的异常.
     */
    public ResultCodeRuntimeException(ResultCode resultCode) {
        this(resultCode, null, null);
    }

    /**
     * 构造一个空的异常.
     */
    public ResultCodeRuntimeException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    /**
     * 构造一个空的异常.
     */
    public ResultCodeRuntimeException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    /**
     * 构造一个空的异常.
     */
    public ResultCodeRuntimeException(ResultCode resultCode, Throwable cause) {
        super((resultCode == null) ? null : resultCode.getMessage().toString(), cause);
        this.resultCode = resultCode;
    }

    /**
     * 取得<code>ResultCode</code>。
     * 
     * @return result code
     */
    public ResultCode getResultCode() {
        return resultCode;
    }
}
