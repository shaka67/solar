/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.rabbitmq;

import com.ouer.solar.config.BaseConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQConfig extends BaseConfig {

	private String host;
	private int port;
	private String virtualHost;
	private String username;
	private String password;
	private int requestedHeartbeat = 10;
	private int connectionTimeout = 60000;
	private int prefetchCount = 1;
    private int concurrentConsumers = 1;
    private long receiveTimeout = 1000;
    private long recoveryInterval = 5000;
    private long startupTimeout = 60000;
    private long shutdownTimeout = 5000;
    private boolean defaultRequeueRejected = true;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	public void setRequestedHeartbeat(int requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getPrefetchCount() {
		return prefetchCount;
	}

	public void setPrefetchCount(int prefetchCount) {
		this.prefetchCount = prefetchCount;
	}

	public int getConcurrentConsumers() {
		return concurrentConsumers;
	}

	public void setConcurrentConsumers(int concurrentConsumers) {
		this.concurrentConsumers = concurrentConsumers;
	}

	public long getReceiveTimeout() {
		return receiveTimeout;
	}

	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	public long getRecoveryInterval() {
		return recoveryInterval;
	}

	public void setRecoveryInterval(long recoveryInterval) {
		this.recoveryInterval = recoveryInterval;
	}

	public long getStartupTimeout() {
		return startupTimeout;
	}

	public void setStartupTimeout(long startupTimeout) {
		this.startupTimeout = startupTimeout;
	}

	public long getShutdownTimeout() {
		return shutdownTimeout;
	}

	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	public boolean isDefaultRequeueRejected() {
		return defaultRequeueRejected;
	}

	public void setDefaultRequeueRejected(boolean defaultRequeueRejected) {
		this.defaultRequeueRejected = defaultRequeueRejected;
	}

	@Override
	public String toString() {
		return "RabbitMQConfig [appId=" + appId + ", host=" + host + ", port="
				+ port + ", virtualHost=" + virtualHost + ", username="
				+ username + ", password=" + password + ", requestedHeartbeat="
				+ requestedHeartbeat + ", connectionTimeout="
				+ connectionTimeout + ", prefetchCount=" + prefetchCount
				+ ", concurrentConsumers=" + concurrentConsumers
				+ ", receiveTimeout=" + receiveTimeout + ", recoveryInterval="
				+ recoveryInterval + ", startupTimeout=" + startupTimeout
				+ ", shutdownTimeout=" + shutdownTimeout
				+ ", defaultRequeueRejected=" + defaultRequeueRejected + "]";
	}

}
