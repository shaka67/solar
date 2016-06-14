/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

/**
 * {@link SimpleMessageSubscriber} forces users to use {@link SimpleMessage} as the
 * converted message content type so the implementor of {@link SimpleMessageSubscriber}
 * can take advantage of the additional data that {@link SimpleMessage} provided.
 *
 * @param <S> The object type that this message subscriber is going to return after
 * processing the given message.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SimpleMessageSubscriber<S> extends MessageSubscriber<SimpleMessage, S> {

}