/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.mogilefs.MogileConstant;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class PoolableSocketFactory implements
		PoolableObjectFactory<SocketConnection>, MogileConstant {

	private static final Logger LOG = LoggerFactory.getLogger(PoolableSocketFactory.class);

	private final InetSocketAddress host;

	public PoolableSocketFactory(InetSocketAddress host) {
		this.host = host;
	}

	@Override
	public SocketConnection makeObject() throws Exception {
		final SocketConnection connection = new SocketConnection(host);
		if (!connection.isConnected()) {
			connection.connecting();
		}

		if (LOG.isTraceEnabled()) {
			LOG.trace("making object [{}]", connection);
        }
		return connection;
	}

	@Override
	public void destroyObject(SocketConnection connection) throws Exception {
		if (LOG.isTraceEnabled()) {
			LOG.trace("destroying object [{}]", connection);
        }
		connection.close();
	}

	@Override
	public boolean validateObject(SocketConnection connection) {
		final boolean connected = connection.isConnected();

		if (!connected) {
			LOG.warn("validating  [{}]. Not valid! Last err was: [{}]",
					connection);
			return connected;
		}

		if (LOG.isTraceEnabled()) {
			LOG.trace("validating [{}]. validated", connection);
		}
		return connected;
	}

	@Override
	public void activateObject(SocketConnection connection) throws Exception {
		// nothing to do
		if (LOG.isTraceEnabled()) {
			LOG.trace("activating object [{}]", connection);
		}

	}

	@Override
	public void passivateObject(SocketConnection connection) throws Exception {
		if (LOG.isTraceEnabled()) {
			LOG.trace("passivating object [{}]", connection);
		}
//		connection.close();
	}

}
