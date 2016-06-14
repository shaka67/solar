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

public class SimpleSearchRequest implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String appId;

	private int resultId;

	private int searchFieldsId;
	private int filterFieldsId = -1;
	private int sortsId = -1;
	private int aggregationsId = -1;
	private int scoreFieldId = -1;

	private Map<String, Object> searchParams;
	private Map<String, Object> filterParams;

	private int page = -1;
    private int pageSize = -1;

    public SimpleSearchRequest(String appId) {
    	this.appId = appId;
    }

    @Override
	public String getAppId() {
		return appId;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public int getSearchFieldsId() {
		return searchFieldsId;
	}

	public void setSearchFieldsId(int searchFieldsId) {
		this.searchFieldsId = searchFieldsId;
	}

	public int getSortsId() {
		return sortsId;
	}

	public void setSortsId(int sortsId) {
		this.sortsId = sortsId;
	}

	public int getAggregationsId() {
		return aggregationsId;
	}

	public void setAggregationsId(int aggregationsId) {
		this.aggregationsId = aggregationsId;
	}

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public Map<String, Object> getFilterParams() {
		return filterParams;
	}

	public void setFilterParams(Map<String, Object> filterParams) {
		this.filterParams = filterParams;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFilterFieldsId() {
		return filterFieldsId;
	}

	public void setFilterFieldsId(int filterFieldsId) {
		this.filterFieldsId = filterFieldsId;
	}

	public int getScoreFieldId() {
		return scoreFieldId;
	}

	public void setScoreFieldId(int scoreFieldId) {
		this.scoreFieldId = scoreFieldId;
	}

}
