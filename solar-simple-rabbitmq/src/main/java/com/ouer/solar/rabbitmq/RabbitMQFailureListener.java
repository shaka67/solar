/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

/**
 * Recover systematic failures with specified error handling mechanism.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RabbitMQFailureListener {

    public void onFailure(Throwable cause);
}