/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MessageProcessorContainerConfig {

	private int prefetchCount;
    private int concurrentConsumers;
    private long receiveTimeout;
    private long recoveryInterval;
    private long startupTimeout;
    private long shutdownTimeout;
    private boolean defaultRequeueRejected;

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

}
