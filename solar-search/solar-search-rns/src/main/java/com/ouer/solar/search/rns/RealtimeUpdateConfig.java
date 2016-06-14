/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rns;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeUpdateConfig {
    
    private int bufferPoolSize;
    private int bufferBatchSize;
    private int bufferThreadSize;
    private int bufferDelay;

	public int getBufferPoolSize() {
		return bufferPoolSize;
	}

	public void setBufferPoolSize(int bufferPoolSize) {
		this.bufferPoolSize = bufferPoolSize;
	}

	public int getBufferBatchSize() {
		return bufferBatchSize;
	}

	public void setBufferBatchSize(int bufferBatchSize) {
		this.bufferBatchSize = bufferBatchSize;
	}

	public int getBufferThreadSize() {
		return bufferThreadSize;
	}

	public void setBufferThreadSize(int bufferThreadSize) {
		this.bufferThreadSize = bufferThreadSize;
	}

	public int getBufferDelay() {
		return bufferDelay;
	}

	public void setBufferDelay(int bufferDelay) {
		this.bufferDelay = bufferDelay;
	}

}
