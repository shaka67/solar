/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * {@link MessageSubscriber} is used to establish interaction contract between
 * {@link MessageListenerAdapter} and message process logic.
 * {@link MessageListenerAdapter} will take an instance of {@link MessageSubscriber}
 * and delegate the converted message to it. Any object returned from <code>onMessage</code>
 * method would be replied back to client.
 *
 * @param <T> The object type that this message subscriber is going to process.
 * @param <S> The object type that this message subscriber is going to return after
 * processing the given message.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MessageSubscriber<T, S> {

    public final static String DEFAULT_METHOD = "onMessage";

    /**
     * @param message An instance of desired object that is converted from raw AMQP message.
     */
    public S onMessage(T message);

}