/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.rabbitmq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.ouer.solar.protocol.HessianObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrier;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 * Build and parse {@link Message} with Hessian binary protocol and support {@link NoDirectDependentCarrierConverter}
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class NDDHessianMessageConverter extends AbstractMessageConverter {

	private static final String CONTENT_TYPE_HESSIAN = "application/x-hessian";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(NDDHessianMessageConverter.class);

    private final ClassMapper mapper;
    private final ObjectConverter converter;
    private final NoDirectDependentCarrierConverter nddCarrierConverter;

    public NDDHessianMessageConverter(NoDirectDependentCarrierConverter nddCarrierConverter) {
        mapper = new HessianClassMapper();
        converter = new HessianObjectConverter();
        this.nddCarrierConverter = nddCarrierConverter;
    }

	@Override
	protected Message createMessage(Object object, MessageProperties properties) {
		byte[] bytes;
        try {
        	if (object instanceof NoDirectDependentCarrier) {
        		final NoDirectDependentCarrier copy = nddCarrierConverter.convert(((NoDirectDependentCarrier) object));
    			object = copy;
    		}

            bytes = converter.toBytes(object);
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
	public Object fromMessage(Message message)
			throws MessageConversionException {
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
