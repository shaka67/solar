/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.google.common.collect.Maps;
import com.ouer.solar.config.utils.ClosableDataSource;
import com.ouer.solar.config.utils.DataSourceUtils;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class DataSourceManager<T extends DataSourceConfig> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	protected final ConfigLocator<T> locator;

	private final ConcurrentMap<String, Future<ClosableDataSource>> datasources = Maps.newConcurrentMap();
	private final ConcurrentMap<String, Future<SqlSessionFactory>> factories = Maps.newConcurrentMap();

	public DataSourceManager(ConfigLocator<T> locator) {
		this.locator = locator;
	}

	public SqlSessionFactory getSqlSessionFactory(final String appId) {
		Future<SqlSessionFactory> future = factories.get(appId);
		if (future == null) {
			final Callable<SqlSessionFactory> callable = new Callable<SqlSessionFactory>() {
				@Override
				public SqlSessionFactory call() {
					try {
						final T config = locator.getConfig(appId);
						return createSqlSessionFactory(config);
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<SqlSessionFactory> task = new FutureTask<SqlSessionFactory>(
					callable);
			future = factories.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			factories.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			factories.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new DataSourceException(e.getCause());
		}
	}

	public void removeSqlSessionFactory(final String appId) throws Exception {
		final Future<SqlSessionFactory> future = factories.remove(appId);
		if (future != null) {
			if (future.isDone()) {
//				future.get().clear();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		removeDataSource(appId);
	}

	public ClosableDataSource getDataSource(final String appId) {
		Future<ClosableDataSource> future = datasources.get(appId);
		if (future == null) {
			final Callable<ClosableDataSource> callable = new Callable<ClosableDataSource>() {
				@Override
				public ClosableDataSource call() {
					try {
						final T config = locator.getConfig(appId);
						return new ClosableDataSource(createDataSource(config));
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<ClosableDataSource> task = new FutureTask<ClosableDataSource>(
					callable);
			future = datasources.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			datasources.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			datasources.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new DataSourceException(e.getCause());
		}
	}

	public void removeDataSource(final String appId) throws Exception {
		final Future<ClosableDataSource> future = datasources.remove(appId);
		if (future != null) {
			if (future.isDone()) {
				future.get().close();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}
	}

	protected SqlSessionFactory createSqlSessionFactory(T config) throws Exception {
		final SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(getConfigFile(config));
		fb.setDataSource(createDataSource(config));
		return fb.getObject();
	}

	protected DataSource createDataSource(T config) throws Exception {
		return DataSourceUtils.createDataSource(config);
	}

	// new or get
	public <M> MapperFactoryBean<M> newMapperFactoryBean(String appId, Class<M> clazz) throws Exception {
		final MapperFactoryBean<M> bean = new MapperFactoryBean<M>();
		bean.setMapperInterface(clazz);
		bean.setSqlSessionFactory(getSqlSessionFactory(appId));
		return bean;
	}

	protected abstract Resource getConfigFile(T config) throws Exception;
}
