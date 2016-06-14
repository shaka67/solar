/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.exception;

import com.ouer.solar.MessageUtil;

/**
 * 参数化的异常，@see MessageUtil
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-22 上午6:24:11
 */
public class MessageException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5502573129887989171L;

    /**
     * 构造一个空的异常.
     */
    public MessageException() {
        super();
    }

    /**
     * 构造一个异常, 指明异常的详细信息.
     * 
     * @param message 详细信息
     */
    public MessageException(String message) {
        super(message);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     * 
     * @param cause 异常的起因
     */
    public MessageException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     * 
     * @param message 详细信息
     * @param cause 异常的起因
     */
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个异常, 参数化详细信息
     * 
     * @param message 详细信息
     * @param params 参数表
     */
    public MessageException(String message, Object...params) {
        super(MessageUtil.formatLogMessage(message, params));
    }

    /**
     * 构造一个异常, 参数化详细信息,指明引起这个异常的起因
     * 
     * @param message 详细信息
     * @param cause 异常的起因
     * @param params 参数表
     */
    public MessageException(String message, Throwable cause, Object...params) {
        super(MessageUtil.formatLogMessage(message, params), cause);
    }

}
