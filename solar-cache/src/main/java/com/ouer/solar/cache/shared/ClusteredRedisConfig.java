/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

import java.util.Set;

import redis.clients.jedis.HostAndPort;

import com.google.common.collect.Sets;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClusteredRedisConfig {

	private Set<HostAndPort> clusterNodes;

	public Set<HostAndPort> getClusterNodes() {
		return clusterNodes;
	}

	public void setHostsAndPorts(String hostsAndPorts) {
		final String[] array = StringUtil.split(hostsAndPorts, StringPool.Symbol.SEMICOLON);
		clusterNodes = Sets.newHashSetWithExpectedSize(array.length);
		String host;
		int port;
		for (final String hostAndPort : array) {
			host = StringUtil.split(hostAndPort, StringPool.Symbol.COLON)[0];
			port = Integer.valueOf(StringUtil.split(hostAndPort, StringPool.Symbol.COLON)[1]);
			clusterNodes.add(new HostAndPort(host, port));
		}
	}

}
