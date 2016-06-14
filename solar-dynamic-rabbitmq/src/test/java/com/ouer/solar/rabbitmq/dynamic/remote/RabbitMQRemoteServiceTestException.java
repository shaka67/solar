/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteServiceTestException extends Exception {

    private final static long serialVersionUID = 1L;

    public RabbitMQRemoteServiceTestException() {
        super();
    }

    public RabbitMQRemoteServiceTestException(String message) {
        super(message);
    }

    public RabbitMQRemoteServiceTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RabbitMQRemoteServiceTestException(Throwable cause) {
        super(cause);
    }

}