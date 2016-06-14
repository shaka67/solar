/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.TrackerCommunicationException;
import com.ouer.solar.mogilefs.pool.PoolableSocketFactory;
import com.ouer.solar.mogilefs.pool.SocketConnection;
import com.ouer.solar.mogilefs.request.MogileRequest;
import com.ouer.solar.nio.NIOBufferedInputStream;
import com.ouer.solar.nio.NIOOutputStream;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogileExecutor implements MogileConstant {

	private static final Logger LOG = LoggerFactory.getLogger(MogileExecutor.class);

	private Trackers trackers;
	private String lastErr;
	private String lastErrStr;
	private SocketReaderAndWriter cachedSocket;
	private Map<InetSocketAddress, ObjectPool<SocketConnection>> socketPoolMap;

	protected ObjectPool<SocketConnection> getSocketPool(InetSocketAddress host) {
		ObjectPool<SocketConnection> socketPool = socketPoolMap.get(host);
		if (socketPool != null) {
            return socketPool;
        }

		socketPool = buildSocketPool(host);
		socketPoolMap.put(host, socketPool);
		return socketPool;
	}

	// FIXME
	protected ObjectPool<SocketConnection> buildSocketPool(
			InetSocketAddress host) {
		// FIXME
		return new GenericObjectPool<SocketConnection>(
				new PoolableSocketFactory(host),
				trackers.getMaxTrackerConnections(),
				GenericObjectPool.WHEN_EXHAUSTED_BLOCK, 1000 * 60, // wait for
																	// up to 60
																	// seconds
																	// if we run
																	// out
				trackers.getMaxIdleConnections(), 1, // minIdle (** 1? **)
				true, // test on borrow
				true, // test on return
				20 * 1000, // time between eviction runs millis
				-1, // number of tests per eviction run
				trackers.getMaxIdleTimeMillis(), // number of seconds before an
													// object is
				// considered idle
				true, // test while idle
				5 * 1000); // softMinEvictableIdleTimeMillis
	}

	public MogileExecutor(Trackers trackers) throws NoTrackersException {
		reload(trackers);
	}

	private void reload(Trackers trackers) throws NoTrackersException {
		this.trackers = trackers;
		socketPoolMap = Maps.newHashMap();

		if (CollectionUtil.isEmpty(trackers.getAddresses())) {
			throw new NoTrackersException();
		}
		final SocketConnection connection = borrowSocket();
		try {
			cachedSocket = new SocketReaderAndWriter(connection);
		} catch (final IOException e) {
			LOG.error("reload error", e);
			if (cachedSocket != null) {
				invalidateSocket(connection);
				cachedSocket = null;
			}
		}

	}

	private ObjectPool<SocketConnection> getSocketPool()
			throws NoTrackersException {
		final int hostSize = trackers.size();
		int tries = (hostSize > TRY_MAX) ? TRY_MAX : hostSize;

		while (tries-- > 0) {
			final InetSocketAddress host = trackers.next();

			try {

				return getSocketPool(host);

			} catch (final Exception e) {
				LOG.error(
						"Unable to connect to tracker at " + host.toString(), e);

			}
		}

		throw new NoTrackersException();
	}

	SocketConnection borrowSocket() throws NoTrackersException {
		ObjectPool<SocketConnection> socketPool = null;

		try {
			socketPool = getSocketPool();

			if (LOG.isInfoEnabled()) {
				LOG.info("getting socket (active: [{}], idle: [{}])",
						socketPool.getNumActive(), socketPool.getNumIdle());
            }

			final SocketConnection connection = socketPool.borrowObject();

			if (LOG.isInfoEnabled()) {
				LOG.info("got socket (active: [{}], idle: [{}])",
						socketPool.getNumActive(), socketPool.getNumIdle());
            }
			return connection;

		} catch (final Exception e) {
			LOG.error("unable to get socket", e);
			LOG.warn("got executor (active: [{}], idle: [{}])",
					socketPool.getNumActive(), socketPool.getNumIdle());
			throw new NoTrackersException();
		}
	}

	void invalidateSocket(SocketConnection connection) {
		try {
			final ObjectPool<SocketConnection> socketPool = socketPoolMap
					.get(connection.getHost());

			if (LOG.isInfoEnabled()) {
				LOG.info(
						"begin invalidating socket (active: [{}], idle: [{}])",
						socketPool.getNumActive(), socketPool.getNumIdle());
            }

			socketPool.invalidateObject(connection);
			cachedSocket.close();
			if (LOG.isInfoEnabled()) {
				LOG.info(
						"end invalidated socket (active: [{}], idle: [{}])",
						socketPool.getNumActive(), socketPool.getNumIdle());
            }

		} catch (final Exception e) {
			// I think we can ignore this
			LOG.error("unable to invalidate socket", e);
		}
	}

	void returnSocket(SocketConnection connection) {
		final ObjectPool<SocketConnection> socketPool = socketPoolMap.get(connection
				.getHost());
		try {

			if (LOG.isInfoEnabled()) {
				LOG.info(
						"begin returning host:[{}]socket (active: [{}], idle: [{}])",
						connection.getHost(), socketPool.getNumActive(),
						socketPool.getNumIdle());
            }

			socketPool.returnObject(connection);

			if (LOG.isInfoEnabled()) {
				LOG.info(
						"end returning host:[{}]socket  (active: [{}], idle: [{}])",
						connection.getHost(), socketPool.getNumActive(),
						socketPool.getNumIdle());
            }

		} catch (final Exception e) {
			// I think we can ignore this.
			LOG.error("unable to return socket", e);
		}
	}

	public Map<String, String> execute(MogileRequest mogileRequest)
			throws NoTrackersException, TrackerCommunicationException {

		final String command = mogileRequest.getCommand();
		final Map<String, String> arguments = mogileRequest.getArguments();
		if ((command == null) || (arguments == null)) {
			LOG.error("null command or args sent to doRequest");
			return null;
		}
		final String argString = encodeURLString(arguments);
		final String request = command + " " + argString + "\r\n";

		if (LOG.isDebugEnabled()) {
			LOG.debug("command: [{}]", request);
		}
		if (cachedSocket != null) {
			// try our cached socket, but assume it might be bogus
			try {
				cachedSocket.getWriter().write(request);
				cachedSocket.getWriter().flush();

			} catch (final IOException e) {
				LOG.error("cached socket went bad while sending request", e);
				invalidateSocket(cachedSocket.getConnection());
			}
			return decodeResponse(false);
		}

		final SocketConnection connection = borrowSocket();
		SocketReaderAndWriter socket;

		try {
			socket = new SocketReaderAndWriter(connection);
			socket.getWriter().write(request);
			socket.getWriter().flush();

		} catch (final IOException e) {
			throw new TrackerCommunicationException(
					"problem finding a working tracker in this list: "
							+ listKnownTrackers());

		}

		cachedSocket = socket;

		return decodeResponse(true);
	}

	private Map<String, String> decodeResponse(boolean usePool)
			throws TrackerCommunicationException {
		try {
			// ok - we finally got a message off to a tracker
			// now get a response
			final String response = cachedSocket.getReader().readLine();

			if (response == null) {
                throw new TrackerCommunicationException(
						"received null response from tracker at "
								+ cachedSocket.getSocket().socket()
										.getInetAddress());
            }

			if (LOG.isDebugEnabled()) {
				LOG.debug("response: [{}]" + response);
			}

			final Matcher ok = OK_PATTERN.matcher(response);
			if (ok.matches()) {
				// good response
				return decodeURLString(ok.group(ARGS_PART));
			}

			final Matcher err = ERROR_PATTERN.matcher(response);
			if (err.matches()) {
				// error response
				lastErr = err.group(ERR_PART);
				lastErrStr = err.group(ERRSTR_PART);

				if (LOG.isDebugEnabled()) {
					LOG.debug("error message from tracker: [{}], [{}]",
							lastErr, lastErrStr);
                }

				return null;
			}

			throw new TrackerCommunicationException(
					"invalid server response from "
							+ cachedSocket.getSocket().socket()
									.getInetAddress() + ": " + response);

		} catch (final IOException e) {
			InetAddress address = null;
			if (cachedSocket != null) {
				address = cachedSocket.getSocket().socket().getInetAddress();
				// problem reading the response
				LOG.warn("problem reading response from server (" + address
						+ ")", e);
				invalidateSocket(cachedSocket.getConnection());
				cachedSocket = null;
			}

			throw new TrackerCommunicationException(
					"problem talking to server at " + address, e);
		} finally {
			if (cachedSocket != null && usePool) {
				returnSocket(cachedSocket.getConnection());
			}
		}
	}

	public String getLastErr() {
		return lastErr;
	}

	public String getLastErrStr() {
		return lastErrStr;
	}

	private String listKnownTrackers() {
		return trackers.getAddresses().toString();
	}

	private String encodeURLString(Map<String, String> args) {
		try {
			final StringBuilder encoded = new StringBuilder();

			for (final Map.Entry<String, String> entry : args.entrySet()) {
				final String key = entry.getKey();
				final String value = entry.getValue();

				if (encoded.length() > 0) {
                    encoded.append("&");
                }
				encoded.append(key);
				encoded.append("=");
				encoded.append(URLEncoder.encode(value, "UTF-8"));
			}

			return encoded.toString();

		} catch (final UnsupportedEncodingException e) {
			LOG.error("problem encoding URL for tracker", e);

			return null;
		}
	}

	/**
	 * Decode a urlencoded string into a bunch of (key, value) pairs
	 *
	 * @param encoded
	 * @return only returns null if java has a problem decoding UTF-8, which
	 *         should never happen
	 */

	private Map<String, String> decodeURLString(String encoded) {
		final Map<String, String> map = Maps.newHashMap();
		try {
			if (StringUtil.isBlank(encoded)) {
                return map;
            }

			final String parts[] = encoded.split("&");
			for (final String part : parts) {
				final String pair[] = part.split("=");

				if ((pair == null) || (pair.length != 2)) {
					LOG.error("poorly encoded string: [{}]", encoded);
					continue;
				}

				map.put(pair[0], URLDecoder.decode(pair[1], "UTF-8"));
			}

			return map;

		} catch (final UnsupportedEncodingException e) {
			LOG.error("problem decoding URL from tracker", e);

			return null;
		}
	}

	/**
	 * Retrieve the name of the tracker we're talking to. Might return null.
	 *
	 * @return
	 */

	public String getTracker() {
		if (cachedSocket == null) {
            return null;
        }

		return cachedSocket.getTracker();
	}

	/**
	 * Close any open connections we've got
	 *
	 */

	public void destroy() {
		if (cachedSocket != null) {
			cachedSocket.close();
		}
	}

	/**
	 * Return true if we're connected to a remote socket
	 */

	public boolean isConnected() {
		return ((cachedSocket != null) && (cachedSocket.getSocket()
				.isConnected()));
	}
}

class SocketReaderAndWriter {

	private static Logger logger = LoggerFactory
			.getLogger(SocketReaderAndWriter.class);

	private SocketChannel socketChannel;

	private final BufferedReader reader;

	private final Writer writer;

	private final SocketConnection connection;

	public SocketReaderAndWriter(SocketConnection connection)
			throws IOException {
		this.connection = connection;
		this.socketChannel = connection.getSocketChannel();
		final InputStream inputStream = new NIOBufferedInputStream(socketChannel);

		reader = new BufferedReader(new InputStreamReader(inputStream));
		final OutputStream outputStream = new NIOOutputStream(socketChannel);
		writer = new OutputStreamWriter(outputStream);
	}

	/**
	 * @return Returns the reader.
	 */
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * @return Returns the socket.
	 */
	public SocketChannel getSocket() {
		return socketChannel;
	}

	/**
	 * @return Returns the writer.
	 */
	public Writer getWriter() {
		return writer;
	}

	public SocketConnection getConnection() {
		return connection;
	}

	/**
	 * Make sure the socket is closed
	 */

	public void close() {
		if (writer != null) {
			try {
				writer.flush();

			} catch (final IOException e) {
				logger.error("writer.flush()", e);
			}
		}
		StreamUtil.close(reader);
		StreamUtil.close(writer);

		if (socketChannel != null) {

			try {
				socketChannel.close();

			} catch (final IOException e) {
				logger.error("socketChannel.close()", e);
			}
			socketChannel = null;
		}
	}

	// protected void finalize() {
	// close();
	// }

	public String getTracker() {
		return socketChannel.socket().getInetAddress().getHostName();
	}

}
