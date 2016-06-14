/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

/**
 * Encapsulate converted content object as well as additional {@link Message} information.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleMessage {

    private final Object content;
    private final Message message;
    
    public SimpleMessage(Message message, Object content) {
        this.content = content;
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public Message getMessage() {
        return message;
    }

    public MessageProperties getProperties() {
        return getMessage().getMessageProperties();
    }

    public String getAppId() {
        return getProperties().getAppId();
    }

    public String getUserId() {
        return getProperties().getUserId();
    }

    public String getMessageId() {
        return "[Exchange: " + getExchangeName() + ", RoutingKey: " + getRoutingKey() +  "]";
    }

    public String getRoutingKey() {
        return getProperties().getReceivedRoutingKey();
    }

    public String getExchangeName() {
        return getProperties().getReceivedExchange();
    }

}