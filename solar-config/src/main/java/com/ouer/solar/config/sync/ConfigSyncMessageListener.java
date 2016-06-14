/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.bus.BusSignalManager;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ConfigSyncMessageListener implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigSyncMessageListener.class);

	private final String key;
	private final BusSignalManager bsm;

	public ConfigSyncMessageListener(String key, BusSignalManager bsm) {
		this.key = key;
		this.bsm = bsm;
	}

	@Override
	public void onMessage(Message message) {
		final String data = new String(message.getBody());
		if (!key.equals(data)) {
			// should not happen
			LOG.warn("listener key is {}, but recevied a message {}, ignore it!", key, data);
			return;
		}

		final String appId = StringUtil.split(data, StringPool.Symbol.COLON)[0];
		final String configType = StringUtil.split(data, StringPool.Symbol.COLON)[1];

		if (LOG.isInfoEnabled()) {
			LOG.info("received config sync message for appId {}, configType {}", appId, configType);
		}

		bsm.signal(new ConfigSyncEvent(appId, configType));
	}

}
