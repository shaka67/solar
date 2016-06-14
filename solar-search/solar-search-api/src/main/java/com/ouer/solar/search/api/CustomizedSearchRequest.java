/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CustomizedSearchRequest implements Serializable, AppConfigurable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String appId;

	private final int resultId;

	private final Map<String, SearchFieldAndValue> fieldValues = Maps.newHashMap();
	private final Map<String, FilterFieldAndValue> filterValues = Maps.newHashMap();
    private final List<SortField> sorts = Lists.newArrayList();
    private final List<AggregationDefinition> aggregations = Lists.newArrayList();
    private ScoreField scoreField;

    private int page = -1;
    private int pageSize = -1;

    public CustomizedSearchRequest(String appId, int resultId) {
    	this.appId = appId;
    	this.resultId = resultId;
    }

	@Override
	public String getAppId() {
		return appId;
	}

	public int getResultId() {
		return resultId;
	}

	public Map<String, SearchFieldAndValue> getFieldValues() {
        return fieldValues;
    }

    public SearchFieldAndValue getFieldValue(String name) {
        return fieldValues.get(name);
    }

    public void addField(SearchFieldAndValue fieldValue) throws SearchRequestException {
        if (fieldValues.containsKey(fieldValue.getField().getName())) {
            throw new SearchRequestException("duplicated field : " + fieldValue.getField().getName() + " in search request");
        }
        fieldValues.put(fieldValue.getField().getName(), fieldValue);
    }

    public Map<String, FilterFieldAndValue> getFilterValues() {
        return filterValues;
    }

    public FilterFieldAndValue getFilterValue(String name) {
        return filterValues.get(name);
    }

    public void addFilter(FilterFieldAndValue filterValue) throws SearchRequestException {
        if (filterValues.containsKey(filterValue.getFilter().getName())) {
            throw new SearchRequestException("duplicated filter : " + filterValue.getFilter().getName() + " in search request");
        }
        filterValues.put(filterValue.getFilter().getName(), filterValue);
    }

    public List<SortField> getSortFields() {
    	return sorts;
    }

    public void addSort(SortField sort) throws SearchRequestException {
        if (sorts.contains(sort)) {
            throw new SearchRequestException("duplicated sort field : " + sort.getName() + " in search request");
        }
        sorts.add(sort);
    }

	public List<AggregationDefinition> getAggregations() {
		return aggregations;
	}

	public void addAggregation(AggregationDefinition aggregation) throws SearchRequestException {
		if (aggregations.contains(aggregation)) {
            throw new SearchRequestException("duplicated aggregation : " + aggregation.getName() + " in search request");
        }
		aggregations.add(aggregation);
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

	public ScoreField getScoreField() {
		return scoreField;
	}

	public void setScoreField(ScoreField scoreField) {
		this.scoreField = scoreField;
	}

}
