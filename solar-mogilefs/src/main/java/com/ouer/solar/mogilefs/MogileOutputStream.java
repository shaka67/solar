/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import static com.ouer.solar.StringPool.Symbol.NEWLINE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.pool.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.mogilefs.exception.NoTrackersException;
import com.ouer.solar.mogilefs.exception.StorageCommunicationException;
import com.ouer.solar.mogilefs.request.MogileRequest;
import com.ouer.solar.nio.NIOBufferedInputStream;
import com.ouer.solar.nio.NIOOutputStream;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MogileOutputStream extends OutputStream implements MogileConstant {

	private static final Logger LOG = LoggerFactory.getLogger(MogileOutputStream.class);

	private ObjectPool<MogileExecutor> executorPool;
	private String domain;
	private String fid;
	private String path;
	private String devid;
	private String key;
	private long totalBytes;
	private SocketChannel socketChannel;
	private OutputStream out;
	private BufferedReader reader;
	private int count;
	private URL parsedPath;

	public static class MogileOutputBuilder {
		public int socketTimeout = MogileOutputStream.SOCKET_TIMEOUT;

		private ObjectPool<MogileExecutor> executorPool;
		private String domain;
		private String fid;
		private String path;
		private String devid;
		private String key;
		private long totalBytes;

		public MogileOutputBuilder socketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}

		public MogileOutputBuilder executorPool(
				ObjectPool<MogileExecutor> executorPool) {
			this.executorPool = executorPool;
			return this;
		}

		public MogileOutputBuilder domain(String domain) {
			this.domain = domain;
			return this;
		}

		public MogileOutputBuilder fid(String fid) {
			this.fid = fid;
			return this;
		}

		public MogileOutputBuilder path(String path) {
			this.path = path;
			return this;
		}

		public MogileOutputBuilder devid(String devid) {
			this.devid = devid;
			return this;
		}

		public MogileOutputBuilder key(String key) {
			this.key = key;
			return this;
		}

		public MogileOutputBuilder totalBytes(long totalBytes) {
			this.totalBytes = totalBytes;
			return this;
		}

		public MogileOutputStream build() throws MalformedURLException,
				StorageCommunicationException {
			return new MogileOutputStream(this);
		}

		public MogileOutputStream build(boolean writeHead)
				throws MalformedURLException, StorageCommunicationException {
			return new MogileOutputStream(this, writeHead);
		}

	}

	private MogileOutputStream(MogileOutputBuilder builder)
			throws MalformedURLException, StorageCommunicationException {
		this(builder, true);
	}

	private MogileOutputStream(MogileOutputBuilder builder, boolean writeHead)
			throws MalformedURLException, StorageCommunicationException {
		this.executorPool = builder.executorPool;
		this.domain = builder.domain;
		this.fid = builder.fid;
		this.path = builder.path;
		this.devid = builder.devid;
		this.key = builder.key;
		this.totalBytes = builder.totalBytes;

		if (writeHead) {
			openSocketAndWriteHead();
			return;
		}
		openSocket();
	}

	private void openSocket() throws StorageCommunicationException {
		try {
			open();
		} catch (final IOException e) {
			throw new StorageCommunicationException(
					"problem initiating communication with storage server before storing "
							+ path + ": " + e.getMessage(), e);
		}
	}

	private void open() throws IOException {
		socketChannel = SocketChannel.open();
		socketChannel.socket().setSoTimeout(SOCKET_TIMEOUT);
		parsedPath = new URL(path);
		socketChannel.connect(new InetSocketAddress(parsedPath.getHost(),
				parsedPath.getPort()));

		out = new NIOOutputStream(socketChannel);
		final InputStream inputStream = new NIOBufferedInputStream(socketChannel);
		reader = new BufferedReader(new InputStreamReader(inputStream));

	}

	private void writeHead() throws IOException {
		final Writer writer = new OutputStreamWriter(out);
		writer.write("PUT ");
		writer.write(parsedPath.getPath());
		writer.write(" HTTP/1.0\r\nContent-length: ");
		// writer.write(" HTTP/1.1\r\nContent-length: ");
		writer.write(Long.toString(totalBytes));
		writer.write("\r\n\r\n");
		writer.flush();
	}

	private void openSocketAndWriteHead() throws StorageCommunicationException {
		try {
			open();
			writeHead();
		} catch (final IOException e) {
			throw new StorageCommunicationException(
					"problem initiating communication with storage server before storing "
							+ path + ": " + e.getMessage(), e);
		}
	}

	private void responseError(String response, Matcher matcher)
			throws IOException {
		if (!matcher.find()) {
			throw new IOException("response from put to " + path
					+ " not understood: " + response);
		}

		final int responseCode = Integer.parseInt(matcher.group(1));
		if ((responseCode < 200) || (responseCode > 299)) {
			// we got an error - read through to the body
			final StringBuilder fullResponse = new StringBuilder();
			fullResponse.append("Problem storing to ");
			fullResponse.append(path);
			fullResponse.append(NEWLINE).append(NEWLINE);
			fullResponse.append(response);
			fullResponse.append(NEWLINE);
			while ((response = reader.readLine()) != null) {
				fullResponse.append(response);
				fullResponse.append(NEWLINE);
			}

			throw new IOException(fullResponse.toString());
		}
	}

	@Override
    public void close() throws IOException {
		if ((out == null) || (socketChannel == null)) {
            throw new IOException("socket has been closed already");
        }

		out.flush();

		final String response = reader.readLine();
		if (response == null) {
            throw new IOException("no response after putting file to "
					+ path.toString());
        }

		final Matcher matcher = VALID_HTTP_RESPONSE.matcher(response);
		this.responseError(response, matcher);

		MogileExecutor executor = null;
		try {
			executor = borrowExecutor();
			if (executor == null) {
				throw new NoTrackersException("no executor used");
			}

			final MogileRequest request = new MogileRequest();
			request.setDomain(domain).setKey(key).addArgument("fid", fid)
					.addArgument("devid", devid)
					.addArgument("size", Long.toString(totalBytes))
					.addArgument("path", path)
					.setOperation(MogileOperation.CREATE_CLOSE);

			final Map<String, String> closeResponse = executor.execute(request);
			if (closeResponse == null) {
				throw new IOException(executor.getLastErrStr());
			}
		} catch (final Exception e) {
			// you know, I could throw this in the conditional above, but this
			// just seems clearer to me for some reason...
			if (executor != null) {
				invalidateExecutor(executor);
				executor = null;
			}

			throw new IOException(e.getMessage());
		} finally {
			StreamUtil.close(out);
			StreamUtil.close(reader);

			socketChannel.close();
			socketChannel = null;
			if (executor != null) {
                returnExecutor(executor);
            }
		}
	}

	@Override
    public void flush() throws IOException {
		if ((out == null) || (socketChannel == null)) {
            throw new IOException("socket has been closed already");
        }

		out.flush();
	}

	@Override
    public void write(int b) throws IOException {
		if ((out == null) || (socketChannel == null)) {
            throw new IOException("socket has been closed already");
        }

		try {
			count++;
			out.write(b);
		} catch (final IOException e) {
			LOG.error(
					"wrote at most [{}]/[{}] of stream to storage node [{}]",
					count, totalBytes, socketChannel.socket().getInetAddress()
							.getHostName());
			throw e;
		}
	}

	@Override
    public void write(byte[] b, int off, int len) throws IOException {
		if ((out == null) || (socketChannel == null)) {
            throw new IOException("socket has been closed already");
        }

		try {
			count += len;
			out.write(b, off, len);
		} catch (final IOException e) {
			LOG.error(
					"wrote at most [{}]/[{}] of stream to storage node [{}]",
					count, totalBytes, socketChannel.socket().getInetAddress()
							.getHostName());
			throw e;
		}
	}

	@Override
    public void write(byte[] b) throws IOException {
		if ((out == null) || (socketChannel == null)) {
            throw new IOException("socket has been closed already");
        }

		try {
			count += b.length;
			out.write(b);
		} catch (final IOException e) {
			LOG.error(
					"wrote at most [{}]/[{}] of stream to storage node [{}]",
					count, totalBytes, socketChannel.socket().getInetAddress()
							.getHostName());
			throw e;
		}
	}

	private MogileExecutor borrowExecutor() throws NoTrackersException {
		try {
			return executorPool.borrowObject();
		} catch (final Exception e) {
			LOG.error("", e);
			throw new NoTrackersException();
		}
	}

	private void returnExecutor(MogileExecutor executor) {
		try {
			executorPool.returnObject(executor);
		} catch (final Exception e) {
			// I think we can ignore this.
			LOG.error("", e);
		}
	}

	private void invalidateExecutor(MogileExecutor executor) {
		try {
			executorPool.invalidateObject(executor);
		} catch (final Exception e) {
			LOG.error("", e);
		}
	}
}
