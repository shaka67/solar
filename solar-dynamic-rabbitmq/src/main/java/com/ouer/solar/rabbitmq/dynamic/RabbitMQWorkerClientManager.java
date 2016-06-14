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

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.collect.Maps;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerClient;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQWorkerClientManager extends RabbitMQManager {

	private final ConcurrentMap<String, Future<RabbitMQRemoteWorkerClient>> workerClients = Maps.newConcurrentMap();

	public RabbitMQWorkerClientManager(RabbitMQConfigLocator locator,
							  		 MessageConverter converter,
							  		 NoDirectDependentCarrierConverter nddCarrierConverter) {
		super(locator, converter, nddCarrierConverter);
	}

	public RabbitMQRemoteWorkerClient getWorkerClient(final String appId) {
		Future<RabbitMQRemoteWorkerClient> future = workerClients.get(appId);
		if (future == null) {
			final Callable<RabbitMQRemoteWorkerClient> callable = new Callable<RabbitMQRemoteWorkerClient>() {
				@Override
				public RabbitMQRemoteWorkerClient call()
						throws InterruptedException {
					CachingConnectionFactory factory;
					try {
						factory = getCachingConnectionFactory(appId);
						return createWorkerClient(factory);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<RabbitMQRemoteWorkerClient> task = new FutureTask<RabbitMQRemoteWorkerClient>(
					callable);
			future = workerClients.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			workerClients.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			workerClients.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeWorkerClient(String appId) throws Exception {
		final Future<RabbitMQRemoteWorkerClient> future = workerClients.remove(appId);
		if (future != null && !future.isDone() && !future.isCancelled()) {
			future.cancel(true);
		}

		removeCachingConnectionFactory(appId);
	}

	private RabbitMQRemoteWorkerClient createWorkerClient(CachingConnectionFactory factory) {
		final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        template.setReplyTimeout(DEFAULT_REPLY_TIMEOUT);
        return new RabbitMQRemoteWorkerClient(template);
	}

}
