/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.collect.Maps;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.rabbitmq.RabbitMQAdmin;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQManager {

	static final int DEFAULT_CACHE_SIZE = 5;
	static final int DEFAULT_REPLY_TIMEOUT = 5 * 60 * 1000; // 5 minutes

	protected final Log LOG = LogFactory.getLog(getClass());

	protected final RabbitMQConfigLocator locator;
	protected final MessageConverter converter;
	protected final NoDirectDependentCarrierConverter nddCarrierConverter;

	private final ConcurrentMap<String, Future<CachingConnectionFactory>> ccfs = Maps.newConcurrentMap();
	private final ConcurrentMap<String, Future<AmqpAdmin>> admins = Maps.newConcurrentMap();

	public RabbitMQManager(RabbitMQConfigLocator locator,
							   MessageConverter converter,
							   NoDirectDependentCarrierConverter nddCarrierConverter) {
		this.locator = locator;
		this.converter = converter;
		this.nddCarrierConverter = nddCarrierConverter;
	}

	public CachingConnectionFactory getCachingConnectionFactory(final String appId) {
		Future<CachingConnectionFactory> future = ccfs.get(appId);
		if (future == null) {
			final Callable<CachingConnectionFactory> callable = new Callable<CachingConnectionFactory>() {
				@Override
				public CachingConnectionFactory call()
						throws InterruptedException {
					final RabbitMQConfig config = locator.getConfig(appId);
					return createConnectionFactory(config);
				}
			};
			final FutureTask<CachingConnectionFactory> task = new FutureTask<CachingConnectionFactory>(
					callable);
			future = ccfs.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			ccfs.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			ccfs.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeCachingConnectionFactory(String appId) throws Exception {
		final Future<CachingConnectionFactory> future = ccfs.remove(appId);
		if (future == null) {
			return;
		}

		if (future.isDone()) {
			future.get().destroy();
			return;
		}
		if (future.isCancelled()) {
			return;
		}
		future.cancel(true);
	}

	public SimpleMessageListenerContainer createSimpleContainer(final String appId) throws Throwable {
		final CachingConnectionFactory factory = getCachingConnectionFactory(appId);
		if (factory == null) {
			return null;
		}
		return new SimpleMessageListenerContainer(factory);
	}

	protected CachingConnectionFactory createConnectionFactory(RabbitMQConfig config) {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(config.getHost());
		factory.setPort(config.getPort());
		factory.setUsername(config.getUsername());
		factory.setPassword(config.getPassword());
		factory.setVirtualHost(config.getVirtualHost());
		factory.setRequestedHeartbeat(config.getRequestedHeartbeat());
		factory.setConnectionTimeout(config.getConnectionTimeout());
		final CachingConnectionFactory caching = new CachingConnectionFactory(factory);
        caching.setChannelCacheSize(DEFAULT_CACHE_SIZE);
        return caching;
	}

	public AmqpAdmin getAmqpAdmin(final String appId)  {
		Future<AmqpAdmin> future = admins.get(appId);
		if (future == null) {
			final Callable<AmqpAdmin> callable = new Callable<AmqpAdmin>() {
				@Override
				public AmqpAdmin call()
						throws InterruptedException {
					try {
						final CachingConnectionFactory factory = getCachingConnectionFactory(appId);
						return createAmqpAdmin(factory);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<AmqpAdmin> task = new FutureTask<AmqpAdmin>(
					callable);
			future = admins.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			admins.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			admins.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeAmqpAdmin(String appId) throws Exception {
		final Future<AmqpAdmin> future = admins.remove(appId);
		if (future != null && !future.isDone() && !future.isCancelled()) {
			future.cancel(true);
		}

		removeCachingConnectionFactory(appId);
	}

	protected AmqpAdmin createAmqpAdmin(CachingConnectionFactory factory) {
		final RabbitTemplate template = new RabbitTemplate(factory);
		template.setMessageConverter(converter);
		template.setReplyTimeout(DEFAULT_REPLY_TIMEOUT);
		return new RabbitMQAdmin(template);
	}
}
