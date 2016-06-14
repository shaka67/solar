/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.MogileException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.StorageCommunicationException;
import com.ouer.solar.mogilefs.exception.TrackerCommunicationException;
import com.ouer.solar.mogilefs.request.MogileRequest;
import com.ouer.solar.nio.FileChannelUtil;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class BaseMogilefs implements Mogilefs, MogileConstant {
	// FIXME
	private static final AtomicInteger ERROR_COUNT = new AtomicInteger();

	protected Logger LOG = LoggerFactory.getLogger(this.getClass());

	protected Trackers trackers;
	private final String domain;
	protected final String httpPrefix;

	private ObjectPool<MogileExecutor> cachedExecutorPool;

	private int maxRetries = TRY_MAX;
	private int retrySleepTime = RETRY_SLEEP_TIME;

	public BaseMogilefs(Trackers trackers, String domain, String httpPrefix)
			throws BadHostFormatException, NoTrackersException {
		this.trackers = trackers;
		this.domain = domain;
		this.httpPrefix = httpPrefix;
	}

	protected abstract ObjectPool<MogileExecutor> buildExecutorPool();

	protected ObjectPool<MogileExecutor> getExecutorPool() {
		if (cachedExecutorPool == null) {
			cachedExecutorPool = buildExecutorPool();
		}
		return cachedExecutorPool;
	}

	@Override
    public OutputStream newFile(String key, String storageClass, long byteCount)
			throws NoTrackersException, TrackerCommunicationException,
			StorageCommunicationException {
		MogileExecutor executor = null;

		try {
			executor = borrowExecutor();

			final Map<String, String> response = this.createOpenCommand(executor,
					storageClass, key);

			if (response == null) {
				throw new TrackerCommunicationException(executor.getLastErr()
						+ ", " + executor.getLastErrStr());
			}

			if ((response.get("path") == null) || (response.get("fid") == null)) {
				throw createOpenException(executor);
			}

			try {
				return (byteCount > 0) ? createMogileOutputStream(response,
						key, byteCount) : createMogileOutputStream(response,
						key);

			} catch (final MalformedURLException e) {
				// hrmm.. this shouldn't happen - we'll blame it on the tracker
				final String error = "error trying to store file with malformed url: "
						+ response.get(MOGILE_PATH);
				LOG.error(error);
				throw new TrackerCommunicationException(error);

			}

		} catch (final TrackerCommunicationException e) {
			// lets nuke this executor connection and make a new one
			if (executor != null) {
				invalidateExecutor(executor);
				executor = null;
			}

			throw e;

		} finally {
			// make sure to return the executor to the pool
			if (executor != null) {
				returnExecutor(executor);
			}
		}
	}

	@Override
	public void storeFile(String key, File file) throws MogileException {
		storeFile(key, "", file);
	}

	@Override
	public void storeFile(String key, String storageClass,
			InputStream inputStream) throws MogileException {
		OutputStream output = null;
		try {
			output = this.newFile(key, storageClass, inputStream.available());
			// FileChannelUtil.copy(inputStream, output);
			StreamUtil.io(inputStream, output);
		} catch (final IOException e) {

			throw new StorageCommunicationException(e.getMessage());
		} finally {
			StreamUtil.close(output);
			StreamUtil.close(inputStream);
		}

	}

	@Override
	public void storeFile(String key, InputStream inputStream)
			throws MogileException {
		storeFile(key, "", inputStream);
	}

	@Override
    public void storeFile(String key, String storageClass, File file)
			throws MogileException {
		int attempt = 1;

		MogileExecutor executor = null;

		while ((maxRetries == -1) || (attempt++ <= maxRetries)) {
			try {
				executor = borrowExecutor();

				final Map<String, String> response = this.createOpenCommand(executor,
						storageClass, key);

				if (response == null) {
					LOG.warn(
							"problem talking to executor: [{}] (err: [{}])",
							executor.getLastErrStr(), executor.getLastErr());
					continue;

				}
				OutputStream out = null;
				InputStream in = null;
				try {
					out = createMogileOutputStream(response, key, file.length());

					in = new FileInputStream(file);
					FileChannelUtil.copy(in, out);
					// success!
					return;

				} catch (final IOException e) {
					LOG.error("error trying to store file", e);
				} finally {
					StreamUtil.close(out);
					StreamUtil.close(in);
				}

			} catch (final MogileException e) {
				LOG.error("problem trying to store file on mogile", e);
				// something went wrong - get rid of the executor object
				if (executor != null) {
					invalidateExecutor(executor);
					executor = null;
				}

			} finally {
				// make sure if we've still got a executor object that
				// we return it to the pool
				if (executor != null) {
                    returnExecutor(executor);
                }
			}

			// wait a little while before continuing
			sleep();

			LOG.info(
					"Error storing file to mogile - attempting to reconnect and try again (attempt #[{}])",
					attempt);
		}

		throw new MogileException(
				"Unable to store file on mogile after multiple attempts");
	}

	// FIXME
	@Override
	public void storeFiles(Map<String, InputStream> streams, String storageClass)
			throws MogileException {
		try {
			for (final Map.Entry<String, InputStream> entry : streams.entrySet()) {
				final String key = entry.getKey();
				final InputStream input = entry.getValue();

				final OutputStream output = this.newFile(key, storageClass,
						input.available());
				// FileChannelUtil.copyAndClose(input, output);
				StreamUtil.io(input, output);
				StreamUtil.close(output);
				StreamUtil.close(input);
			}

		} catch (final IOException e) {
			throw new StorageCommunicationException(e.getMessage());
		}
	}

	@Override
	public void storeFiles(Map<String, InputStream> streams)
			throws MogileException {
		storeFiles(streams, "");
	}

	private void sleep() {
		if (retrySleepTime > 0) {
			try {
				Thread.sleep(retrySleepTime);
			} catch (final InterruptedException e) {
				LOG.error("sleep InterruptedException", e);
			}

		}
	}

	@Override
    public File getFile(String key, File destination)
			throws NoTrackersException, TrackerCommunicationException,
			IOException, StorageCommunicationException {
		final InputStream in = getFileStream(key);

		if (in == null) {
			return null;
		}

		final OutputStream out = new FileOutputStream(destination);

		try {
			FileChannelUtil.copy(in, out);
			// StreamUtil.copy(in, out);
			return destination;
		} finally {
			out.close();
			in.close();
		}

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
		InputStream inputStream = null;
		for (int tries = paths.length; tries > 0; tries--) {
			final String path = paths[startIndex++ % tries];

			try {
				final URL pathURL = new URL(path);
				if (LOG.isDebugEnabled()) {
					LOG.debug("retrieving file from [{}] (attempt #[{}])",
							path, (Arrays.hashCode(paths) - tries));
                }

				inputStream = pathURL.openStream();
				return inputStream;

			} catch (final IOException e) {
				LOG.error("problem reading file from [{}]", path);
				StreamUtil.close(inputStream);
			}
		}

		throw storageCommunicationException(key, paths);
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

		InputStream inputStream = null;
		for (int tries = paths.length; tries > 0; tries--) {
			final String path = paths[startIndex++ % tries];

			try {
				final URL pathURL = new URL(path);
				if (LOG.isDebugEnabled()) {
					LOG.debug("retrieving file from [{}] (attempt #[{}])",
							path, (Arrays.hashCode(paths) - tries));
                }

				final HttpURLConnection conn = (HttpURLConnection) pathURL
						.openConnection();
				inputStream = conn.getInputStream();

				return StreamUtil.readBytesByFast(inputStream,
						conn.getContentLength());

			} catch (final IOException e) {
				LOG.error("problem reading file from [{}]", path);
			} finally {
				StreamUtil.close(inputStream);
			}
		}

		throw storageCommunicationException(key, paths);

	}

	protected StorageCommunicationException storageCommunicationException(
			String key, String[] paths) throws StorageCommunicationException {
		final StringBuilder pathString = new StringBuilder();
		for (int i = 0; i < paths.length; i++) {
			if (i > 0) {
                pathString.append(", ");
            }
			pathString.append(paths[i]);
		}

		throw new StorageCommunicationException(
				"unable to retrieve file with key '" + key
						+ "' from any storage node: " + pathString);
	}

	@Override
    public InputStream getFileStream(String key) throws NoTrackersException,
			TrackerCommunicationException, StorageCommunicationException {
		return getFileStream(key, false);
	}

	@Override
    public void delete(String key) throws NoTrackersException,
			NoTrackersException {
		int attempt = 1;

		MogileExecutor executor = null;

		while ((maxRetries == -1) || (attempt++ <= maxRetries)) {
			try {
				executor = borrowExecutor();
				final MogileRequest request = new MogileRequest();
				request.setDomain(domain).setKey(key)
						.setOperation(MogileOperation.DELETE);

				executor.execute(request);

				return;

			} catch (final TrackerCommunicationException e) {
				LOG.error("", e);

				if (executor != null) {
					invalidateExecutor(executor);
					executor = null;
				}

			} finally {
				if (executor != null) {
                    returnExecutor(executor);
                }
			}

			// something went wrong - so wait a little while before continuing
			sleep();
		}

		throw new NoTrackersException();
	}

	@Override
    public void sleep(int seconds) throws NoTrackersException,
			TrackerCommunicationException {
		MogileExecutor executor = null;

		try {
			executor = borrowExecutor();
			final MogileRequest request = new MogileRequest();
			request.addArgument("duration", Integer.toString(seconds))
					.setOperation(MogileOperation.SLEEP);

			executor.execute(request);

		} finally {
			if (executor != null) {
                returnExecutor(executor);
            }
		}

	}

	@Override
    public void rename(String fromKey, String toKey) throws NoTrackersException {
		int attempt = 1;

		MogileExecutor executor = null;

		while ((maxRetries == -1) || (attempt++ <= maxRetries)) {
			try {
				executor = borrowExecutor();

				final MogileRequest request = new MogileRequest();
				request.addArgument("from_key", fromKey)
						.addArgument("to_key", toKey)
						.setOperation(MogileOperation.RENAME);

				executor.execute(request);

				return;

			} catch (final TrackerCommunicationException e) {
				LOG.error("", e);

				if (executor != null) {
					invalidateExecutor(executor);
					executor = null;
				}

			} finally {
				if (executor != null) {
                    returnExecutor(executor);
                }
			}
			// something went wrong - so wait a little while before continuing
			sleep();
		}

		throw new NoTrackersException();
	}

	@Override
    public boolean exists(String key) throws NoTrackersException {
		return getPaths(key, true) != null;
	}

	@Override
    public String[] getPaths(String key, boolean noverify)
			throws NoTrackersException {
		int attempt = 1;

		MogileExecutor executor = null;

		while ((maxRetries == -1) || (attempt++ <= maxRetries)) {
			try {
				executor = borrowExecutor();

				final MogileRequest request = new MogileRequest();
				request.addArgument("noverify", (noverify ? "1" : "0"))
						.setDomain(domain).setKey(key)
						.setOperation(MogileOperation.GET_PATHS);

				final Map<String, String> response = executor.execute(request);
				if (response == null) {
					return null;
				}

				final int pathCount = Integer.parseInt(response.get("paths"));
				final String[] paths = new String[pathCount];
				for (int i = 1; i <= pathCount; i++) {
					final String path = response.get("path" + i);
					paths[i - 1] = path;
				}

				return paths;

			} catch (final TrackerCommunicationException e) {
				LOG.error("", e);

				if (executor != null) {
					invalidateExecutor(executor);
					executor = null;
				}

			} finally {
				if (executor != null) {
                    returnExecutor(executor);
                }
			}
			// something went wrong - so wait a little while before continuing
			sleep();
		}

		throw new NoTrackersException();
	}

	@Override
    public String getDomain() {
		return domain;
	}

	MogileExecutor borrowExecutor() throws NoTrackersException {
		ObjectPool<MogileExecutor> executorPool = null;
		try {
			executorPool = getExecutorPool();

			if (LOG.isDebugEnabled()) {
				LOG.debug("getting executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }

			final MogileExecutor executor = executorPool.borrowObject();

			if (LOG.isDebugEnabled()) {
				LOG.debug("got executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }
			return executor;

		} catch (final Exception e) {
			LOG.error("unable to get executor", e);
			LOG.warn("got executor (active: [{}], idle: [{}])",
					executorPool.getNumActive(), executorPool.getNumIdle());

			if (ERROR_COUNT.get() < ERROR_MAX) {
				ERROR_COUNT.getAndIncrement();
				throw new NoTrackersException();
			}
			cachedExecutorPool = buildExecutorPool();
			ERROR_COUNT.compareAndSet(ERROR_MAX, 0);
			throw new NoTrackersException();
		}
	}

	void returnExecutor(MogileExecutor executor) {
		try {
			final ObjectPool<MogileExecutor> executorPool = getExecutorPool();

			if (LOG.isDebugEnabled()) {
				LOG.debug(
						"begin returning executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }

			executorPool.returnObject(executor);

			if (LOG.isDebugEnabled()) {
				LOG.debug(
						"end returning executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }

		} catch (final Exception e) {
			// I think we can ignore this.
			LOG.error("unable to return executor", e);
		}
	}

	void invalidateExecutor(MogileExecutor executor) {
		try {
			final ObjectPool<MogileExecutor> executorPool = getExecutorPool();

			if (LOG.isDebugEnabled()) {
				LOG.debug(
						"begin invalidating executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }

			executorPool.invalidateObject(executor);

			if (LOG.isDebugEnabled()) {
				LOG.debug(
						"end invalidated executor (active: [{}], idle: [{}])",
						executorPool.getNumActive(), executorPool.getNumIdle());
            }

		} catch (final Exception e) {
			// I think we can ignore this
			LOG.error("unable to invalidate executor", e);
		}
	}

	private TrackerCommunicationException createOpenException(
			MogileExecutor executor) {
		return new TrackerCommunicationException(
				"create_open response from tracker " + executor.getTracker()
						+ " missing fid or path (err:" + executor.getLastErr()
						+ ", " + executor.getLastErrStr() + ")");
	}

	private MogileOutputStream createMogileOutputStream(
			Map<String, String> response, String key, long byteCount)
			throws MalformedURLException, StorageCommunicationException {

		return new MogileOutputStream.MogileOutputBuilder()
				.executorPool(getExecutorPool()).domain(domain)
				.fid(response.get(MOGILE_FID)).path(response.get(MOGILE_PATH))
				.devid(response.get(MOGILE_DEVID)).key(key)
				.totalBytes(byteCount).build();
	}

	private MogileOutputStream createMogileOutputStream(
			Map<String, String> response, String key)
			throws MalformedURLException, StorageCommunicationException {

		return new MogileOutputStream.MogileOutputBuilder()
				.executorPool(getExecutorPool()).domain(domain)
				.fid(response.get(MOGILE_FID)).path(response.get(MOGILE_PATH))
				.devid(response.get(MOGILE_DEVID)).key(key).build(false);
	}

	private Map<String, String> createOpenCommand(MogileExecutor executor,
			String storageClass, String key) throws NoTrackersException,
			TrackerCommunicationException {

		final MogileRequest request = new MogileRequest();
		request.setDomain(domain).setStorageClass(storageClass).setKey(key)
				.setOperation(MogileOperation.CREATE_OPEN);

		return executor.execute(request);
	}

	/**
	 * Set the max number of times to try retry storing a file with 'storeFile'
	 * or deleting a file with 'delete'. If this is -1, then never stop
	 * retrying. This value defaults to -1.
	 *
	 * @param maxRetries
	 */
	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	/**
	 * After a failed 'storeFile' request, sleep for this number of milliseconds
	 * before retrying the store. Defaults to 2 seconds.
	 *
	 * @param retrySleepTime
	 */
	public void setRetryTimeout(int retrySleepTime) {
		this.retrySleepTime = retrySleepTime;
	}

	@Override
	public String getUrl(String key) {
		return httpPrefix + key;
	}

	@Override
	public void stop() throws Exception {
		if (cachedExecutorPool != null) {
			cachedExecutorPool.close();
		}
	}
}