/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.ouer.solar.protocol.HessianObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;

/**
 * Build and parse {@link Message} with Hessian binary protocol.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class HessianMessageConverter extends AbstractMessageConverter {

    private final static String CONTENT_TYPE_HESSIAN = "application/x-hessian";
    private final static String DEFAULT_CHARSET = "UTF-8";
    
    private final static Log LOG = LogFactory.getLog(HessianMessageConverter.class);
    
    private final ClassMapper mapper;
    private final ObjectConverter converter;
    
    public HessianMessageConverter() {
        mapper = new HessianClassMapper();
        converter = new HessianObjectConverter();
    }

    @Override
    protected Message createMessage(Object object, MessageProperties properties) {
        byte[] bytes;
        try {
            bytes =  converter.toBytes(object);
        } catch (final IOException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }
        properties.setContentType(CONTENT_TYPE_HESSIAN);
        properties.setContentEncoding(DEFAULT_CHARSET);
        properties.setContentLength(bytes.length);
        mapper.fromClass(object.getClass(), properties);
        return new Message(bytes, properties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        final MessageProperties properties = message.getMessageProperties();
        if (properties == null) {
            return message.getBody();
        }
        final String contentType = properties.getContentType();
        if (contentType == null || !contentType.contains("hessian")) {
            LOG.warn("Could not convert incoming message with content-type [" + contentType + "]");
            return message.getBody();
        }
        String encoding = properties.getContentEncoding();
        if (encoding == null) {
            encoding = DEFAULT_CHARSET;
        }
        final Class<?> targetClass = mapper.toClass(message.getMessageProperties());
        Object object;
        try {
            object = converter.fromBytes(message.getBody(), targetClass);
        } catch (final IOException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }
        return object;
    }

}