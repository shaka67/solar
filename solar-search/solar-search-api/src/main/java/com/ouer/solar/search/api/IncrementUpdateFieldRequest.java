/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;
import java.util.Map;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IncrementUpdateFieldRequest implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String appId;
	private final int indexId;
	private final Map<Long, Map<String, Object>> records;

	public IncrementUpdateFieldRequest(String appId, int indexId, Map<Long, Map<String, Object>> records) {
		this.appId = appId;
		this.indexId = indexId;
		this.records = records;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public int getIndexId() {
		return indexId;
	}

	public Map<Long, Map<String,Object>> getRecords() {
		return records;
	}
}
