/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Override certain base methods in {@link MessageListenerAdapter} in order to
 * provide additional information (such as {@link Message} and {@link MessageProperties})
 * for creating {@link SimpleMessage}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleMessageListenerAdapater extends MessageListenerAdapter {

    @Override
    protected Object extractMessage(Message message) {
        final Object content = super.extractMessage(message);
        return new SimpleMessage(message, content);
    }

}