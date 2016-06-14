/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexBuildConfig {

    private int pageSize;
    private int indexPartionNum;
    private int waitTime;
    private long timeoutMillis;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIndexPartionNum() {
		return indexPartionNum;
	}

	public void setIndexPartionNum(int indexPartionNum) {
		this.indexPartionNum = indexPartionNum;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public long getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(long timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

}
