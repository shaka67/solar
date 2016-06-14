/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access;

import java.io.IOException;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午7:28:16
 */
public class AccessException extends IOException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4040415771373665799L;

    /**
     * 构造一个空的异常.
     */
    public AccessException() {
        super();
    }

    /**
     * 构造一个异常, 指明异常的详细信息.
     * 
     * @param message 详细信息
     */
    public AccessException(String message) {
        super(message);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     * 
     * @param cause 异常的起因
     */
    public AccessException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个异常, 指明引起这个异常的起因.
     * 
     * @param message 详细信息
     * @param cause 异常的起因
     */
    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
