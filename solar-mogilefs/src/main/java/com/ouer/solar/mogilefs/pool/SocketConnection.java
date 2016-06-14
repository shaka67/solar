/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.ouer.solar.mogilefs.MogileConstant;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SocketConnection implements MogileConstant {

	private final SocketChannel socketChannel;

	private final InetSocketAddress host;

	public SocketConnection(InetSocketAddress host) throws IOException {
		this.host = host;
		socketChannel = SocketChannel.open();
		socketChannel.socket().setSoTimeout(SOCKET_TIMEOUT);
	}

	public void connecting() throws IOException {
		socketChannel.socket().connect(host, CONNECT_TIMEOUT);
	}

	public boolean isConnected() {
		return socketChannel.isConnected();
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public InetSocketAddress getHost() {
		return host;
	}

	public void close() throws IOException {
		if (socketChannel != null) {
			socketChannel.close();
		}
	}

}
