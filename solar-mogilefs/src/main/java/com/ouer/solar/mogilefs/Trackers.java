/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ouer.solar.StringUtil;
import com.ouer.solar.loadbalance.LoadBalancer;
import com.ouer.solar.loadbalance.RoundRobin;
import com.ouer.solar.mogilefs.exception.BadHostFormatException;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Trackers implements MogileConstant {

	private static final Logger LOG = LoggerFactory.getLogger(Trackers.class);

	private List<InetSocketAddress> hosts;
	private int maxTrackerConnections = 100;
	private int maxIdleConnections = 10;
	// 10 minutes
	private long maxIdleTimeMillis = 1000L * 60L * 10L;
	private LoadBalancer loadBalancer = new RoundRobin();

	public InetSocketAddress next() {
		final int index = loadBalancer.next();
		return hosts.get(index);
	}

	public int index() {
		return loadBalancer.next();
	}

	public int size() {
		return hosts.size();
	}

	public LoadBalancer getLoadBalancer() {
		return loadBalancer;
	}

	public Trackers setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
		return this;
	}

	public List<InetSocketAddress> getAddresses() {
		return hosts;
	}

	public Trackers setHosts(String hosts) {
		final String[] hostArray = StringUtil.split(hosts, " ,/");

		try {
			this.hosts = parseHosts(hostArray);
		} catch (final BadHostFormatException e) {
			LOG.error("BadHostFormatException hosts=[{}]", hosts);
			System.exit(1);
		}
		loadBalancer.setTotal(this.hosts.size());
		return this;
	}

	public void addHost(InetSocketAddress host) {
		hosts.add(host);
	}

	private List<InetSocketAddress> parseHosts(String[] hostArray)
			throws BadHostFormatException {
		final List<InetSocketAddress> list = Lists.newArrayListWithExpectedSize(hostArray.length);

		for (final String host : hostArray) {
			final Matcher m = HOST_PORT_PATTERN.matcher(host);
			if (!m.matches()) {
                throw new BadHostFormatException(host);
            }

			if (LOG.isDebugEnabled()) {
				LOG.debug("parsed tracker [{}]", host);
			}

			final InetSocketAddress addr = new InetSocketAddress(m.group(1),
					Integer.parseInt(m.group(2)));
			list.add(addr);
		}

		return list;
	}

	public int getMaxTrackerConnections() {
		return maxTrackerConnections;
	}

	public Trackers setMaxTrackerConnections(int maxTrackerConnections) {
		this.maxTrackerConnections = maxTrackerConnections;
		return this;
	}

	public int getMaxIdleConnections() {
		return maxIdleConnections;
	}

	public Trackers setMaxIdleConnections(int maxIdleConnections) {
		this.maxIdleConnections = maxIdleConnections;
		return this;
	}

	public long getMaxIdleTimeMillis() {
		return maxIdleTimeMillis;
	}

	public Trackers setMaxIdleTimeMillis(long maxIdleTimeMillis) {
		this.maxIdleTimeMillis = maxIdleTimeMillis;
		return this;
	}

}
