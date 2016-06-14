/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.spring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import com.ouer.solar.rabbitmq.RabbitMQAdmin;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@Configuration
@ImportResource("classpath:META-INF/applicationContext-simple-rabbitmq.xml")
public class RabbitMQConfiguration {

	private static final int DEFAULT_CACHE_SIZE = 5;
	private static final int DEFAULT_REPLY_TIMEOUT = 5 * 60 * 1000; // 5 minutes

	@Autowired
	@Bean
	@Scope("prototype")
	SimpleMessageListenerContainer provideSimpleMessageListenerContainer(CachingConnectionFactory factory) {
        return new SimpleMessageListenerContainer(factory);
    }

	@Autowired
	@Bean
	AmqpAdmin amqpAdmin(RabbitTemplate template)
    {
		return new RabbitMQAdmin(template);
    }

	@Autowired
	@Bean
	RabbitTemplate rabbitTemplate(CachingConnectionFactory factory, MessageConverter converter)
    {
        final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        template.setReplyTimeout(DEFAULT_REPLY_TIMEOUT);
        return template;
    }

	@Autowired
	@Bean
	CachingConnectionFactory cachingConnectionFactory(ConnectionFactory factory)
    {
        final CachingConnectionFactory caching = new CachingConnectionFactory(factory);
        caching.setChannelCacheSize(DEFAULT_CACHE_SIZE);
        return caching;
    }

	@Bean
	ExecutorService executorService()
    {
        return Executors.newCachedThreadPool(new ThreadFactory() {
        	private static final String NAME_PREFIX = "message-processor-";
        	private final AtomicInteger number = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                final Thread thread = new Thread(r, NAME_PREFIX + number.getAndIncrement());
                return thread;
            }

        });
    }

}
