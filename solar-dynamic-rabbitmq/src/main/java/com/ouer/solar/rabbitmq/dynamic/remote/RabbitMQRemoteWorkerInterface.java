/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;


/**
 * Provide common remote worker constants that would be used for both client and server side.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RabbitMQRemoteWorkerInterface {

    public static final String DEFAULT_REMOTE_WORKER_EXCHANGE_PREFIX = "solar.exchange.worker.";

    public static final String DEFAULT_REMOTE_WORKER_QUEUE_PREFIX = "solar.queue.worker.";

    public static final String QUALIFIER_NAME = "REMOTE.WORKER";
}