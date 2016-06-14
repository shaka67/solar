/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import java.util.List;

import redis.clients.jedis.JedisShardInfo;

import com.google.common.collect.Lists;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.cache.CacheException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ShardedRedisConfig {

	private String[] hosts;
	private Integer[] ports;
	private String[] names;
	private Integer[] timeouts;

	private List<JedisShardInfo> shardInfos;

	public void setHosts(String hosts) {
		this.hosts = StringUtil.split(hosts, StringPool.Symbol.SEMICOLON);
	}

	public void setPorts(String ports) {
		final String[] array = StringUtil.split(ports, StringPool.Symbol.SEMICOLON);
		this.ports = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			this.ports[i] = Integer.valueOf(array[i]);
		}
	}

	public void setNames(String names) {
		this.names = StringUtil.split(names, StringPool.Symbol.SEMICOLON);
	}

	public void setTimeouts(String timeouts) {
		final String[] array = StringUtil.split(timeouts, StringPool.Symbol.SEMICOLON);
		this.timeouts = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			this.timeouts[i] = Integer.valueOf(array[i]);
		}
	}

	public List<JedisShardInfo> getShardInfos() {
		if (shardInfos != null) {
			return shardInfos;
		}

		if (hosts.length != ports.length) {
			throw new CacheException("invalid shard config for redis hosts and redis ports");
		}
		if (hosts.length != names.length) {
			throw new CacheException("invalid shard config for redis hosts and redis names");
		}
		if (hosts.length != timeouts.length) {
			throw new CacheException("invalid shard config for redis hosts and redis timeouts");
		}

		shardInfos = Lists.newArrayList();
		JedisShardInfo info;
		for (int i = 0; i < hosts.length; i++) {
			info = new JedisShardInfo(hosts[i], ports[i], timeouts[i], names[i]);
			shardInfos.add(info);
		}
		return shardInfos;
	}
}
