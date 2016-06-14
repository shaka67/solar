/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQDynamicException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RabbitMQDynamicException() {
        super();
    }

    public RabbitMQDynamicException(String message) {
        super(message);
    }

    public RabbitMQDynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public RabbitMQDynamicException(Throwable cause) {
        super(cause);
    }
}
