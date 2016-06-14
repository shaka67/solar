/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs.dynamic;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.config.search.SearchConfig;
import com.ouer.solar.config.search.SearchConfigLocator;
import com.ouer.solar.jobclient.Job;
import com.ouer.solar.jobclient.JobSchedulerClient;
import com.ouer.solar.jobclient.JobStatus;
import com.ouer.solar.search.common.TransportClientManager;
import com.ouer.solar.search.dal.IndexMapper;
import com.ouer.solar.search.dal.dynamic.SearchDalManager;
import com.ouer.solar.search.definition.DefinitionFactory;
import com.ouer.solar.search.definition.DefinitionFactoryManager;
import com.ouer.solar.search.ibs.IbsException;
import com.ouer.solar.search.ibs.IndexBuildConfig;
import com.ouer.solar.search.ibs.IndexBuildProxy;
import com.ouer.solar.search.ibs.IndexBuildService;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexBuilderManager {

	private final static Logger LOG = LoggerFactory.getLogger(IndexBuilderManager.class);

	private final IndexBuildConfig config;
	private final SearchConfigLocator locator;
	private final TransportClientManager clientManager;
	private final DefinitionFactoryManager definitionManager;
	private final SearchDalManager dalManager;
	private final JobSchedulerClient jobScheduler;

	private final ConcurrentMap<String, Future<IndexBuildService>> services = Maps.newConcurrentMap();

	public IndexBuilderManager(IndexBuildConfig config,
								  SearchConfigLocator locator,
								  TransportClientManager clientManager,
								  DefinitionFactoryManager definitionManager,
								  SearchDalManager dalManager,
								  JobSchedulerClient jobScheduler) {
		this.config = config;
		this.locator = locator;
		this.clientManager = clientManager;
		this.definitionManager = definitionManager;
		this.dalManager = dalManager;
		this.jobScheduler = jobScheduler;
	}

	public IndexBuildService getIndexBuildService(final String appId) {
		Future<IndexBuildService> future = services.get(appId);
		if (future == null) {
			final Callable<IndexBuildService> callable = new Callable<IndexBuildService>() {
				@Override
				public IndexBuildService call() {
					try {
						final TransportClient client = clientManager.getTransportClient(appId);
						final DefinitionFactory factory = definitionManager.getDefinitionFactory(appId);
						final IndexMapper mapper = dalManager.newMapperFactoryBean(appId, IndexMapper.class).getObject();
						final IndexBuildService service = createIndexBuildService(client, factory, mapper);
						startJob(appId);
						return service;
					} catch (final Throwable e) {
						LOG.error("", e);
						return null;
					}
				}
			};
			final FutureTask<IndexBuildService> task = new FutureTask<IndexBuildService>(
					callable);
			future = services.putIfAbsent(appId, task);
			if (future == null) {
				future = task;
				task.run();
			}
		}
		try {
			return future.get();
		} catch (final InterruptedException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final CancellationException e) {
			LOG.error("", e);
			services.remove(appId, future);
			return null;
		} catch (final ExecutionException e) {
			LOG.error("", e);
			throw new IbsException(e.getCause());
		}
	}

	public void removeIndexBuildService(String appId) throws Exception {
		final Future<IndexBuildService> future = services.remove(appId);
		if (future != null) {
			if (future.isDone()) {
//				future.get().shutdown();
			}
			else if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
		}

		stopJob(appId);
		clientManager.removeTransportClient(appId);
		definitionManager.removeDefinitionFactory(appId);
		dalManager.removeSqlSessionFactory(appId);
	}

	private IndexBuildService createIndexBuildService(TransportClient client,
			 											  DefinitionFactory factory,
			 											  IndexMapper mapper) {
		return new IndexBuildProxy(config, client, factory, mapper);
	}

	public void startJob(String appId) {
		final Job job = createJob(appId);
		final JobStatus status = jobScheduler.rescheduleJob(job);
		if (LOG.isInfoEnabled()) {
			LOG.info("scheduled " + job + " with a status " + status);
		}
	}

	public void stopJob(String appId) {
		final Job job = createJob(appId);
		final JobStatus status = jobScheduler.deleteJob(job);
		if (LOG.isInfoEnabled()) {
			LOG.info("deleted " + job + " with a status " + status);
		}
	}

	private Job createJob(String appId) {
		final SearchConfig config = locator.getConfig(appId);
		final Job job = new Job(appId, "rebuild", "search ibs")
						.withGroup("solar.daily.ibs.build")
						.withCron(config.getJobCronExpression())
						.withArgument(new Object[] {appId});
		return job;
	}
}
