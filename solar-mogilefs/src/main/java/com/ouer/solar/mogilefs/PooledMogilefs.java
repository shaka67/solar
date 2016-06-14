/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import com.ouer.solar.ClassUtil;
import com.ouer.solar.ConcurrentUtil;
import com.ouer.solar.lang.SimpleThreadFactory;
import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.StorageCommunicationException;
import com.ouer.solar.mogilefs.exception.TrackerCommunicationException;
import com.ouer.solar.mogilefs.pool.PoolableExecutorFactory;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class PooledMogilefs extends BaseMogilefs {

	private final int maxTrackerConnections;
	private final int maxIdleConnections;
	private final long maxIdleTimeMillis;

//	private TCPRouter router;

//	private final Set<InetSocketAddress> deadHosts = Sets.newHashSet();

	private final ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor(new SimpleThreadFactory(ClassUtil
					.getShortClassName(PooledMogilefs.class)
					+ "-singleScheduler"));

	private final CloseableHttpAsyncClient httpClient;

	/**
	 * Set things up. Make sure you pass in at least one valid tracker, or
	 * you'll get an exception.
	 *
	 * @throws NoTrackersException
	 *             if we can't connect to at least one tracker
	 */
	public PooledMogilefs(Trackers trackers, String domain, String httpPrefix)
			throws NoTrackersException, BadHostFormatException {
		super(trackers, domain, httpPrefix);

		this.maxIdleConnections = trackers.getMaxIdleConnections();
		this.maxTrackerConnections = trackers.getMaxTrackerConnections();
		this.maxIdleTimeMillis = trackers.getMaxIdleTimeMillis();

		httpClient = HttpAsyncClients.createDefault();

		httpClient.start();
		if (LOG.isInfoEnabled()) {
			LOG.info("[{}] start ", httpClient);
		}
	}

//	public void start() {
//		scheduler.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				final Iterator<Entry<ServerStatus, Connector<NioSocketConnector>>> iter = router
//						.getConnectors().entrySet().iterator();
//
//				while (iter.hasNext()) {
//					final Entry<ServerStatus, Connector<NioSocketConnector>> entry = iter
//							.next();
//					final ServerStatus serverStatus = entry.getKey();
//					if (!entry.getValue().isConnected()) {
//						checkDeadHost(serverStatus);
//					} else {
//						checkAndRecover(serverStatus);
//					}
//				}
//			}
//		}, DEAD_TIME_CHECK, DEAD_TIME_CHECK, TimeUnit.MILLISECONDS);

//		httpClient = HttpAsyncClients.createDefault();
//
//		httpClient.start();
//		if (LOG.isInfoEnabled()) {
//			LOG.info("[{}] start ", httpClient);
//		}
//	}

	@Override
	public void stop() throws Exception {
//		router.stop();
		super.stop();
		ConcurrentUtil.shutdownAndAwaitTermination(scheduler);
		try {
			if (httpClient != null) {
				httpClient.close();
			}
			if (LOG.isInfoEnabled()) {
				LOG.info("[{}] stop ", httpClient);
			}
		} catch (final IOException e) {
			LOG.error("stop error", e);
		}
	}

//	private void checkDeadHost(ServerStatus serverStatus) {
//		final Iterator<InetSocketAddress> iter = trackers.getAddresses().iterator();
//		while (iter.hasNext()) {
//			final InetSocketAddress host = iter.next();
//			if (host.getHostName().equals(serverStatus.getIp())
//					&& host.getPort() == serverStatus.getPort()) {
//				iter.remove();
//				deadHosts.add(host);
//				break;
//			}
//
//		}
//
//	}

//	private void checkAndRecover(ServerStatus serverStatus) {
//		if (CollectionUtil.isEmpty(deadHosts)) {
//			return;
//		}
//		final Iterator<InetSocketAddress> iter = deadHosts.iterator();
//		while (iter.hasNext()) {
//			final InetSocketAddress host = iter.next();
//			if (host.getHostName().equals(serverStatus.getIp())
//					&& host.getPort() == serverStatus.getPort()) {
//				iter.remove();
//				trackers.addHost(host);
//				break;
//			}
//		}
//
//	}

	@Override
    protected synchronized ObjectPool<MogileExecutor> buildExecutorPool() {
		// create a new pool of executor objects
		return new GenericObjectPool<MogileExecutor>(
				new PoolableExecutorFactory(trackers), maxTrackerConnections,
				GenericObjectPool.WHEN_EXHAUSTED_BLOCK, 1000 * 60, // wait for
																	// up to 60
																	// seconds
																	// if we run
																	// out
				maxIdleConnections, 1, // minIdle (** 1? **)
				true, // test on borrow
				true, // test on return
				20 * 1000, // time between eviction runs millis
				-1, // number of tests per eviction run
				maxIdleTimeMillis, // number of seconds before an object is
									// considered idle
				true, // test while idle
				5 * 1000); // softMinEvictableIdleTimeMillis
	}

	@Override
    public byte[] getFileBytes(String key) throws NoTrackersException,
			TrackerCommunicationException, IOException,
			StorageCommunicationException {
		final String paths[] = getPaths(key, false);

		if (paths == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("couldn't find paths for [{}]", key);
			}
			return null;
		}

		int startIndex = trackers.index();
		for (int tries = paths.length; tries > 0; tries--) {
			final String path = paths[startIndex++ % tries];

			try {
				final HttpGet request = new HttpGet(path);
				if (LOG.isDebugEnabled()) {
					LOG.debug("retrieving file from [{}] (attempt #[{}])",
							path, (Arrays.hashCode(paths) - tries));
                }

				final Future<HttpResponse> response = httpClient.execute(request,
						null);
				final HttpResponse httpResponse = response.get();
				if (httpResponse == null) {
					return null;
				}
				return EntityUtils.toByteArray(httpResponse.getEntity());

			} catch (final Exception e) {
				LOG.error("problem reading file from [{}]", path);
			}
		}

		throw storageCommunicationException(key, paths);

	}

	@Override
	public InputStream getFileStream(String key, boolean noverify)
			throws NoTrackersException, TrackerCommunicationException,
			StorageCommunicationException {

		final String paths[] = getPaths(key, noverify);

		if (paths == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("couldn't find paths for [{}]", key);
			}
			return null;
		}

		int startIndex = trackers.index();
		for (int tries = paths.length; tries > 0; tries--) {
			final String path = paths[startIndex++ % tries];

			try {
				final HttpGet request = new HttpGet(path);
				if (LOG.isDebugEnabled()) {
					LOG.debug("retrieving file from [{}] (attempt #[{}])",
							path, (Arrays.hashCode(paths) - tries));
                }

				final Future<HttpResponse> response = httpClient.execute(request,
						null);
				final HttpResponse httpResponse = response.get();
				if (httpResponse == null) {
					return null;
				}
				return httpResponse.getEntity().getContent();

			} catch (final Exception e) {
				LOG.error("problem reading file from [{}]", path);
			}
		}

		throw storageCommunicationException(key, paths);
	}

//	public void setRouter(TCPRouter router) {
//		this.router = router;
//	}

}
