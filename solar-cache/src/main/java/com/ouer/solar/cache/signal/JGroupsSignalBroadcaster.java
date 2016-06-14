/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import java.net.URL;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.StringPool;
import com.ouer.solar.cache.CacheException;
import com.ouer.solar.cache.LevelableCache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JGroupsSignalBroadcaster extends AbstractSignalBroadcaster implements CacheSignalBroadcaster {

	private static final String CLUSTER_NAME = "default";
	private static final String CONFIG_XML = "/jgroups.xml";
	private static final Logger LOG = LoggerFactory.getLogger(JGroupsSignalBroadcaster.class);

	private final JChannel channel;
	private final JGroupsCacheReceiver receiver;

	public JGroupsSignalBroadcaster(LevelableCache cache,
			CacheSignalAssistant csa) {
		super(cache, csa);
		receiver = new JGroupsCacheReceiver();

		try {
			final long time = System.currentTimeMillis();
			URL url = getClass().getResource(CONFIG_XML);
			if(url == null) {
				url = getClass().getClassLoader().getParent().getResource(CONFIG_XML);
			}
			channel = new JChannel(url);
			channel.setReceiver(receiver);
			channel.connect(CLUSTER_NAME);
			if (LOG.isInfoEnabled()) {
				LOG.info("connected to channel:{}, time {} ms.", CLUSTER_NAME, (System.currentTimeMillis() - time));
			}
		} catch(final Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void broadcastEvictSignal(String namespace, Object key) {
		final CacheSignal sginal = csa.create(CacheSignalOperator.EVICT, namespace, key);
		try {
			final Message msg = new Message(null, null, csa.toBytes(sginal));
			channel.send(msg);
		} catch (final Exception e) {
			LOG.error("unable to delete cache, namespace=" + namespace + ",key=" + key, e);
		}
	}

	@Override
	public void broadcastClearSignal(String namespace) {
		final CacheSignal sginal = csa.create(CacheSignalOperator.CLEAR, namespace, StringPool.Symbol.EMPTY);
		try {
			final Message msg = new Message(null, null, csa.toBytes(sginal));
			channel.send(msg);
		} catch (final Exception e) {
			LOG.error("unable to clear cache, namespace=" + namespace, e);
		}
	}

	class JGroupsCacheReceiver extends ReceiverAdapter {

		@Override
		public void receive(Message msg) {
			final byte[] message = msg.getBuffer();
			if (message.length < 1) {
				LOG.warn("message is empty.");
				return;
			}
			if (msg.getSrc().equals(channel.getAddress())) {
				return;
			}
			JGroupsSignalBroadcaster.this.handleValidMessage(message);
		}
	}
}
