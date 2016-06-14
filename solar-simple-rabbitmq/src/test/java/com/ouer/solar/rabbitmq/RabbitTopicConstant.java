/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RabbitTopicConstant {

    public final static String HOST_NAME = "localhost";
    public final static String EXCHANGE_NAME = "exchange.topic.test";
    public final static String ROUTING_KEY_1 = "topic.key.test.1";
    public final static String ROUTING_KEY_2 = "topic.key.prod.1";
    public final static String BINDING_KEY_1 = "topic.key.test.*";
    public final static String BINDING_KEY_2 = "topic.key.#";

}