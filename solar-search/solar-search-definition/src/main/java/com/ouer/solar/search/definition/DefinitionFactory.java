/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;

import com.google.common.collect.Maps;
import com.ouer.solar.search.api.AggregationDefinition;
import com.ouer.solar.search.api.FilterField;
import com.ouer.solar.search.api.ScoreField;
import com.ouer.solar.search.api.SearchField;
import com.ouer.solar.search.api.SortField;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefinitionFactory {

	// index definition
	private final Map<Integer, IndexClass> indexes = Maps.newHashMap();
	// result definition
	private final Map<Integer, ResultClass> results = Maps.newHashMap();
	// search definition
    private final Map<Integer, Map<String, SearchField>> searchFields = Maps.newHashMap();
    private final Map<Integer, Map<String, FilterField>> filterFields = Maps.newHashMap();
	private final Map<Integer, List<SortField>> sortFields = Maps.newHashMap();
	private final Map<Integer, List<AggregationDefinition>> aggregations = Maps.newHashMap();
	private final Map<Integer, ScoreField> scoreFields = Maps.newHashMap();

    public IndexClass getIndexClass(int id) {
        return indexes.get(id);
    }

    void addIndexClass(IndexClass index) throws DefinitionException {
        if (indexes.containsKey(index.getId())) {
            throw new DefinitionException("duplicated key : " + index.getId() + " in index class maps");
        }

        indexes.put(index.getId(), index);
    }

    public ResultClass getResultClass(int id) {
        return results.get(id);
    }

    void addResultClass(ResultClass result) throws DefinitionException {
        if (results.containsKey(result.getId())) {
            throw new DefinitionException("duplicated key : " + result.getId() + " in result class maps");
        }

        results.put(result.getId(), result);
    }

    void addSearchField(int fieldsId, SearchField field) {
    	Map<String, SearchField> map = searchFields.get(fieldsId);
    	if (map == null) {
    		map = Maps.newHashMap();
    		searchFields.put(fieldsId, map);
    	}
    	map.put(field.getName(), field);
    }

    public Map<String, SearchField> getSearchFields(int fieldsId) {
    	return searchFields.get(fieldsId);
    }

    void addFilterField(int fieldsId, FilterField field) {
    	Map<String, FilterField> map = filterFields.get(fieldsId);
    	if (map == null) {
    		map = Maps.newHashMap();
    		filterFields.put(fieldsId, map);
    	}
    	map.put(field.getName(), field);
    }

    public Map<String, FilterField> getFilterFields(int fieldsId) {
    	return filterFields.get(fieldsId);
    }

    void addSortField(int sortsId, SortField sort) {
    	List<SortField> list = sortFields.get(sortsId);
    	if (list == null) {
    		list = Lists.newArrayList();
    		sortFields.put(sortsId, list);
    	}
    	list.add(sort);
    }

    public List<SortField> getSortFields(int sortsId) {
    	return sortFields.get(sortsId);
    }

    void addAggregation(int aggsId, AggregationDefinition aggregation) {
    	List<AggregationDefinition> list = aggregations.get(aggsId);
    	if (list == null) {
    		list = Lists.newArrayList();
    		aggregations.put(aggsId, list);
    	}
    	list.add(aggregation);
    }

    void addScoreField(int scoreFieldId, ScoreField scoreField) {
    	scoreFields.put(scoreFieldId, scoreField);
    }

    public ScoreField getScoreField(int scoreFieldId) {
    	return scoreFields.get(scoreFieldId);
    }

    public List<AggregationDefinition> getAggregations(int aggsId) {
    	return aggregations.get(aggsId);
    }

    public Iterator<Integer> indexIterator() {
        return indexes.keySet().iterator();
    }

    public Iterator<Integer> resultIterator() {
        return results.keySet().iterator();
    }

    public int getIndexIdByResultId(int resultId) {
    	return results.get(resultId).getIndexId();
    }

    public void clear() {
    	indexes.clear();
    	results.clear();
    	searchFields.clear();
    	filterFields.clear();
    	sortFields.clear();
    	aggregations.clear();
    	scoreFields.clear();
    }
}
