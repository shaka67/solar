/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeSearchConfig {

	private int defaultPage;
	private int defaultPageSize;
    private long timeoutMillis;

	public int getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(int defaultPage) {
		this.defaultPage = defaultPage;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public long getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(long timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

}
