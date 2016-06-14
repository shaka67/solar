/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.dynamic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.collect.Maps;
import com.ouer.solar.config.jobserver.JobServerConfigLocator;
import com.ouer.solar.config.jobserver.JobServerSpecialConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfig;
import com.ouer.solar.config.rabbitmq.RabbitMQConfigLocator;
import com.ouer.solar.jobserver.daemon.JobQueueDaemon;
import com.ouer.solar.jobserver.daemon.JobSchedulerDaemon;
import com.ouer.solar.jobserver.queue.JobQueueSubscriber;
import com.ouer.solar.jobserver.scheduler.QuartzJobScheduler;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQDynamicException;
import com.ouer.solar.rabbitmq.dynamic.RabbitMQManager;
import com.ouer.solar.rabbitmq.nnd.NoDirectDependentCarrierConverter;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobServerQueueManager extends RabbitMQManager {

	private static final String QUARTZ_COMMON = "quartz-common.properties";

	private static final Log LOG = LogFactory.getLog(JobServerQueueManager.class);

	private final JobServerConfigLocator jobLocator;
	private final JobFactory jobFactory;

	private final ConcurrentMap<String, Future<Scheduler>> schedulers = Maps.newConcurrentMap();
	private final ConcurrentMap<String, Future<JobSchedulerDaemon>> schedulerDaemons = Maps.newConcurrentMap();
	private final ConcurrentMap<String, Future<JobQueueDaemon>> queueDaemons = Maps.newConcurrentMap();

	public JobServerQueueManager(RabbitMQConfigLocator locator,
									  MessageConverter converter,
									  NoDirectDependentCarrierConverter nddCarrierConverter,
									  JobServerConfigLocator jobLocator,
									  JobFactory jobFactory) {
		super(locator, converter, nddCarrierConverter);
		this.jobLocator = jobLocator;
		this.jobFactory = jobFactory;
	}

	public Scheduler getScheduler(final String appId)  {
		Future<Scheduler> future = schedulers.get(appId);
		if (future == null) {
			final Callable<Scheduler> callable = new Callable<Scheduler>() {
				@Override
				public Scheduler call() {
					try {
						final Properties props = createProperties(appId);
						final SchedulerFactory factory = createSchedulerFactory(props);
						return createScheduler(factory);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<Scheduler> task = new FutureTask<Scheduler>(
					callable);
			future = schedulers.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			schedulers.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			schedulers.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeScheduler(String appId) throws Exception {
		final Future<Scheduler> future = schedulers.remove(appId);
		if (future != null && !future.isDone() && !future.isCancelled()) {
			future.cancel(true);
		}

//		future.get().shutdown();
	}

	public JobSchedulerDaemon getJobSchedulerDaemon(final String appId)  {
		Future<JobSchedulerDaemon> future = schedulerDaemons.get(appId);
		if (future == null) {
			final Callable<JobSchedulerDaemon> callable = new Callable<JobSchedulerDaemon>() {
				@Override
				public JobSchedulerDaemon call() {
					try {
						final Scheduler scheduler = getScheduler(appId);
						return createJobSchedulerDaemon(scheduler);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<JobSchedulerDaemon> task = new FutureTask<JobSchedulerDaemon>(
					callable);
			future = schedulerDaemons.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			schedulerDaemons.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			schedulerDaemons.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeJobSchedulerDaemon(String appId) throws Exception {
		final Future<JobSchedulerDaemon> future = schedulerDaemons.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().shutdown();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		removeScheduler(appId);
	}

	public JobQueueDaemon getJobQueueDaemon(final String appId)  {
		Future<JobQueueDaemon> future = queueDaemons.get(appId);
		if (future == null) {
			final Callable<JobQueueDaemon> callable = new Callable<JobQueueDaemon>() {
				@Override
				public JobQueueDaemon call()
						throws InterruptedException {
					try {
						final RabbitMQConfig config = locator.getConfig(appId);
						final AmqpAdmin admin = getAmqpAdmin(appId);
						final CachingConnectionFactory factory = getCachingConnectionFactory(appId);
						final Scheduler scheduler = getScheduler(appId);
						final JobQueueSubscriber subscriber = createJobQueueSubscriber(scheduler);
						return createJobQueueDaemon(appId, admin, factory, config, subscriber);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<JobQueueDaemon> task = new FutureTask<JobQueueDaemon>(
					callable);
			future = queueDaemons.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			queueDaemons.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			queueDaemons.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new RabbitMQDynamicException(e.getCause());
		}
	}

	public void removeJobQueueDaemon(String appId) throws Exception {
		final Future<JobQueueDaemon> future = queueDaemons.remove(appId);
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

	private Properties createProperties(String appId) {
		final Properties properties = new Properties();
        final String name = QUARTZ_COMMON; // quartz-common.properties
        final InputStream resource = Thread.currentThread().getContextClassLoader()
                                                     .getResourceAsStream(name);
        try
        {
            properties.load(resource);
            final JobServerSpecialConfig config = jobLocator.getConfig(appId);
            addSpecialProps(properties, config);
            return properties;
        }
        catch (final IOException e)
        {
            throw new RuntimeException("Failed to load properties file", e);
        }
	}

	private void addSpecialProps(Properties properties, JobServerSpecialConfig config) {
		properties.setProperty(JobServerSpecialConfig.INSTANCE_NAME, config.getInstanceName());
		properties.setProperty(JobServerSpecialConfig.DATASOURCE_NAME, config.getAppId());
//		properties.setProperty(JobServerSpecialConfig.DB_URL, config.getDbUrl());
//		properties.setProperty(JobServerSpecialConfig.DB_USER, config.getDbUser());
//		properties.setProperty(JobServerSpecialConfig.DB_PASSWORD, config.getDbPassword());
		properties.setProperty(config.getDriverKey(), JobServerSpecialConfig.DEFAULT_DRIVER);
		properties.setProperty(config.getDbUrlKey(), config.getDbUrl());
		properties.setProperty(config.getDbUserKey(), config.getDbUser());
		properties.setProperty(config.getDbPasswordKey(), config.getDbPassword());
	}

	private SchedulerFactory createSchedulerFactory(Properties properties) {
		final StdSchedulerFactory factory = new StdSchedulerFactory();
        try
        {
            factory.initialize(properties);
        }
        catch (final SchedulerException e)
        {
            throw new RuntimeException("Failed to initialize scheduler factory", e);
        }
        return factory;
	}

	private Scheduler createScheduler(SchedulerFactory factory) {
		try {
            final Scheduler scheduler = factory.getScheduler();
            scheduler.setJobFactory(jobFactory);
            return scheduler;
        } catch (final SchedulerException e) {
            final String error = "[ERROR] initialize quartz scheduler: " + e.getMessage();
            throw new RuntimeException(error, e);
        }
	}

	private JobSchedulerDaemon createJobSchedulerDaemon(Scheduler scheduler) {
		return new JobSchedulerDaemon(scheduler);
	}

	private JobQueueSubscriber createJobQueueSubscriber(Scheduler scheduler) {
		final QuartzJobScheduler jobScheduler = new QuartzJobScheduler(scheduler);
		return new JobQueueSubscriber(jobScheduler);
	}

	private JobQueueDaemon createJobQueueDaemon(final String appId,
															AmqpAdmin admin,
			  												ConnectionFactory factory,
			  												RabbitMQConfig config,
			  												JobQueueSubscriber subscriber) {
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

		return new JobQueueDaemon(admin, factory, executor, config, subscriber, converter);
	}

}
