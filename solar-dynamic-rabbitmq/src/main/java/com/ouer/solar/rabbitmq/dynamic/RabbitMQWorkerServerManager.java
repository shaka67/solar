/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.collect.Maps;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerDaemon;
import com.ouer.solar.rabbitmq.dynamic.remote.RabbitMQRemoteWorkerRegister;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQWorkerServerManager extends RabbitMQManager {

    private final static Log LOG = LogFactory.getLog(RabbitMQWorkerServerManager.class);

	private final RabbitMQRemoteWorkerRegister workerRegister;

	private final ConcurrentMap<String, Future<RabbitMQRemoteWorkerDaemon>> workerDaemons = Maps.newConcurrentMap();

	public RabbitMQWorkerServerManager(RabbitMQConfigLocator locator,
											  MessageConverter converter,
											  NoDirectDependentCarrierConverter nddCarrierConverter,
											  RabbitMQRemoteWorkerRegister workerRegister) {
		super(locator, converter, nddCarrierConverter);
		this.workerRegister = workerRegister;
	}

	public RabbitMQRemoteWorkerDaemon getWorkerDaemon(final String appId) {
		Future<RabbitMQRemoteWorkerDaemon> future = workerDaemons.get(appId);
		if (future == null) {
			final Callable<RabbitMQRemoteWorkerDaemon> callable = new Callable<RabbitMQRemoteWorkerDaemon>() {
				@Override
				public RabbitMQRemoteWorkerDaemon call()
						throws InterruptedException {
					try {
						final RabbitMQConfig config = locator.getConfig(appId);
						final AmqpAdmin admin = getAmqpAdmin(appId);
						final CachingConnectionFactory factory = getCachingConnectionFactory(appId);
						return createWorkerDaemon(appId, config, factory, admin);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<RabbitMQRemoteWorkerDaemon> task = new FutureTask<RabbitMQRemoteWorkerDaemon>(
					callable);
			future = workerDaemons.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			workerDaemons.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			workerDaemons.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeWorkerDaemon(String appId) throws Exception {
		final Future<RabbitMQRemoteWorkerDaemon> future = workerDaemons.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().shutdown();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		removeAmqpAdmin(appId);
	}

	private RabbitMQRemoteWorkerDaemon createWorkerDaemon(final String appId,
																	  RabbitMQConfig config,
																	  CachingConnectionFactory factory,
																	  AmqpAdmin admin) {
		final ExecutorService executor = Executors
				.newCachedThreadPool(new ThreadFactory() {
					private static final String NAME_PREFIX = "message-processor-";
					private final AtomicInteger number = new AtomicInteger(1);

					@Override
					public Thread newThread(Runnable r) {
						final Thread thread = new Thread(r, NAME_PREFIX + appId
								+ number.getAndIncrement());
						return thread;
					}

				});

		return new RabbitMQRemoteWorkerDaemon(admin, workerRegister, factory,
				config, executor, nddCarrierConverter);
	}
}
