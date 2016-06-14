/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.reflect.MethodUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.fieldvaluefactor.FieldValueFactorFunctionBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.ValuesSourceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.StringUtil;
import com.ouer.solar.search.api.AggregationDefinition;
import com.ouer.solar.search.api.BoolType;
import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.FilterField;
import com.ouer.solar.search.api.FilterFieldAndValue;
import com.ouer.solar.search.api.FilterType;
import com.ouer.solar.search.api.QueryType;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.RssException;
import com.ouer.solar.search.api.ScoreField;
import com.ouer.solar.search.api.SearchField;
import com.ouer.solar.search.api.SearchFieldAndValue;
import com.ouer.solar.search.api.SimpleSearchRequest;
import com.ouer.solar.search.api.SortField;
import com.ouer.solar.search.definition.DefinitionFactory;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class RealtimeSearchTemplate implements RealtimeSearch { //, MoreLikeSet {

	private static final Logger LOG = LoggerFactory.getLogger(RealtimeSearchTemplate.class);

	private final int id;
	private final RealtimeSearchConfig config;
	private final TransportClient client;
	private final DefinitionFactory factory;
//	private final SearchConfigClient configClient;

	public RealtimeSearchTemplate(int id,
			  						 RealtimeSearchConfig config,
			  						 TransportClient client,
			  						 DefinitionFactory factory) {
		this.id = id;
		this.config = config;
		this.client = client;
		this.factory = factory;
	}

	@Override
	public RealtimeSearchResponse search(SimpleSearchRequest request) throws RssException {
//		DefinitionFactory factory = null;
//		try {
//			factory = configClient.getFactory(appId);
//		} catch (final Exception e) {
//			LOG.error("", e);
//			throw new RssException(e);
//		}

		final String index = factory.getIndexClass(id).getIndex();
		final String type = factory.getIndexClass(id).getType();

		final int resultId = request.getResultId();
		final int searchFieldId = request.getSearchFieldsId();
		final Map<String, SearchField> searchFields = factory.getSearchFields(searchFieldId);
		Map<String, Object> params = request.getSearchParams();
		Iterator<String> it = params.keySet().iterator();
		String key;
		Object value;
		float boost;

		// build query
		final BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolean boolMatch = false;
		BoolType boolType;
		QueryType queryType;
		BoolType multiType;
		boolean constantScore;
		QueryBuilder queryBuilder;
		while (it.hasNext()) {
			key = it.next();
			value = params.get(key);
			boost = searchFields.get(key).getBoost();
			queryType = searchFields.get(key).getQueryType();
			multiType = searchFields.get(key).getMultiType();
			constantScore = searchFields.get(key).isConstantScore();
			queryBuilder = RealtimeSearchUtil.fetchBuilder(queryType, key, value, multiType);
//			if (queryBuilder instanceof BoostableQueryBuilder) {
//				((BoostableQueryBuilder<?>) queryBuilder).boost(boost);
//			}
			if (constantScore) {
				final ConstantScoreQueryBuilder constantScoreQueryBuilder = new ConstantScoreQueryBuilder(queryBuilder);
				constantScoreQueryBuilder.boost(boost);
				queryBuilder = constantScoreQueryBuilder;
			}
			boolType = searchFields.get(key).getBoolType();
			try {
				final Method method = MethodUtils.getAccessibleMethod(boolQueryBuilder.getClass(),
						boolType.desc(), QueryBuilder.class);
				method.invoke(boolQueryBuilder, queryBuilder);
			} catch (final Exception e) {
				throw new RssException(e);
			}

			boolMatch = true;
		}

		// build filter
		boolean filterMatch = false;
		final int filterFieldId = request.getFilterFieldsId();
		final Map<String, FilterField> filterFields = factory.getFilterFields(filterFieldId);
		final AndFilterBuilder filterBuilder = FilterBuilders.andFilter();

		if (filterFields != null) {
			params = request.getFilterParams();
			it = params.keySet().iterator();
			FilterType filterType;
			FilterBuilder builder;
			while (it.hasNext()) {
				key = it.next();
				value = params.get(key);
				filterType = filterFields.get(key).getFilterType();
				builder = RealtimeSearchUtil.fetchBuilder(filterType, key, value);
				filterBuilder.add(builder);
				filterMatch = true;
			}
		} else if (request.getFilterParams() != null) {
			/**
			 * FIXME when code goes here, we mean the term filter set at server side from {@link SearchConfig#parseTermFilters()}
			 */
			it = request.getFilterParams().keySet().iterator();
			FilterBuilder builder;
			while (it.hasNext()) {
				key = it.next();
				value = request.getFilterParams().get(key);
				builder = RealtimeSearchUtil.fetchBuilder(FilterType.TERM, key, value);
				filterBuilder.add(builder);
				filterMatch = true;
			}
		}

		final String[] indices = new String[] { index };
		final String[] types = new String[] { type };

		final SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indices)
				/*.setSearchType(SearchType.DFS_QUERY_AND_FETCH)*/.setTypes(types);
		if (filterMatch) {
			queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, filterBuilder);
		} else if (boolMatch) {
			queryBuilder = boolQueryBuilder;
		} else { // FIXME
			queryBuilder = null;
		}
		// build score field
		final ScoreField scoreField = factory.getScoreField(request.getScoreFieldId());
		if (scoreField != null) {
			FieldValueFactorFunctionBuilder fvffb = new FieldValueFactorFunctionBuilder(scoreField.getName())
										.factor(scoreField.getFactor());
			if (StringUtil.isNotEmpty(scoreField.getModifier())) {
				final FieldValueFactorFunction.Modifier m = FieldValueFactorFunction.Modifier.valueOf(scoreField.getModifier());
				if (m != null) {
					fvffb = fvffb.modifier(m);
				}
			}
			queryBuilder = QueryBuilders.functionScoreQuery(queryBuilder).add(fvffb);
		}
		searchRequestBuilder.setQuery(queryBuilder);

		int page = request.getPage();
		int pageSize = request.getPageSize();
		if (page < 0) {
			page = config.getDefaultPage();
		}
		if (pageSize < 0) {
			pageSize = config.getDefaultPageSize();
		}

		searchRequestBuilder.setFrom(page * pageSize);
		searchRequestBuilder.setSize(pageSize);
		final Iterator<String> fields = factory.getResultClass(resultId).getFields().keySet().iterator();
		String field;
		while (fields.hasNext()) {
			field = fields.next();
			searchRequestBuilder.addField(field);
		}
		// add sort
		final List<SortField> sorts = factory.getSortFields(request.getSortsId());
		if (!CollectionUtil.isEmpty(sorts)) {
			for (final SortField sort : sorts) {
				searchRequestBuilder.addSort(sort.getName(), sort.getOrder().esOrder());
			}
			searchRequestBuilder.addSort(new ScoreSortBuilder());
		}
		// add aggregation
		final List<AggregationDefinition> aggregations = factory.getAggregations(request.getAggregationsId());
		AggregationBuilder<?> builder;
		for (final AggregationDefinition aggregation : aggregations) {
			builder = ReflectionUtil.invokeStaticMethod(AggregationBuilders.class, aggregation.getType(),
					new Class[] { String.class }, aggregation.getName());
			if (builder instanceof ValuesSourceAggregationBuilder) {
				((ValuesSourceAggregationBuilder<?>) builder).field(aggregation.getField());
			} else {
				// TODO
			}
			searchRequestBuilder.addAggregation(builder);
		}

		final SearchResponse response = searchRequestBuilder.execute().actionGet(config.getTimeoutMillis());
		final RealtimeSearchResponse result = new RealtimeSearchResponse();

		final long totalHits = response.getHits().totalHits();
		LOG.info("got total {} results", totalHits);
		result.setTotal(totalHits);
		result.setPage(page);

		for (final SearchHit hit : response.getHits()) {
			result.addHit(RealtimeSearchUtil.convert(hit.fields()));
		}

		MultiBucketsAggregation mbAggregation;
		for (final AggregationDefinition aggregation : aggregations) {
			mbAggregation = response.getAggregations().get(aggregation.getName());
			result.addAggregation(aggregation.getName(), mbAggregation.getBuckets());
		}

		return result;
	}

	@Override
	public RealtimeSearchResponse search(CustomizedSearchRequest request) throws RssException {
//		DefinitionFactory factory = null;
//		try {
//			factory = configClient.getFactory(appId);
//		} catch (final Exception e) {
//			LOG.error("", e);
//			throw new RssException(e);
//		}

		final String index = factory.getIndexClass(id).getIndex();
		final String type = factory.getIndexClass(id).getType();
		final int resultId = request.getResultId();

		final Map<String, SearchFieldAndValue> fieldValues = request.getFieldValues();
		Iterator<String> it = fieldValues.keySet().iterator();
		String key;
		Object value;
		SearchFieldAndValue fieldValue;
		float boost;
		boolean boolMatch = false;
		BoolType boolType;
		QueryType queryType;
		BoolType multiType;
		boolean constantScore;
		QueryBuilder queryBuilder;
		final BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		while (it.hasNext()) {
			key = it.next();
			fieldValue = fieldValues.get(key);
			value = fieldValue.getValue();
			queryType = fieldValue.getField().getQueryType();
			multiType = fieldValue.getField().getMultiType();
			constantScore = fieldValue.getField().isConstantScore();
			queryBuilder = RealtimeSearchUtil.fetchBuilder(queryType, key, value, multiType);
//			if (queryBuilder instanceof BoostableQueryBuilder) {
//				boost = fieldValue.getField().getBoost();
//				((BoostableQueryBuilder<?>) queryBuilder).boost(boost);
//			}
			if (constantScore) {
				final ConstantScoreQueryBuilder constantScoreQueryBuilder = new ConstantScoreQueryBuilder(queryBuilder);
				boost = fieldValue.getField().getBoost();
				constantScoreQueryBuilder.boost(boost);
				queryBuilder = constantScoreQueryBuilder;
			}
			boolType = fieldValue.getField().getBoolType();
			try {
				final Method method = MethodUtils.getAccessibleMethod(boolQueryBuilder.getClass(),
						boolType.desc(), QueryBuilder.class);
				method.invoke(boolQueryBuilder, queryBuilder);
			} catch (final Exception e) {
				throw new RssException(e);
			}
			boolMatch = true;
		}
		// build filter
		boolean filterMatch = false;
		final Map<String, FilterFieldAndValue> filterValues = request.getFilterValues();
		final AndFilterBuilder filterBuilder = FilterBuilders.andFilter();
		if (!CollectionUtil.isEmpty(filterValues)) {
			it = filterValues.keySet().iterator();
			FilterType filterType;
			FilterBuilder builder;
			FilterFieldAndValue filterValue;
			while (it.hasNext()) {
				key = it.next();
				filterValue = filterValues.get(key);
				value = filterValue.getValue();
				filterType = filterValue.getFilter().getFilterType();
				builder = RealtimeSearchUtil.fetchBuilder(filterType, key, value);
				filterBuilder.add(builder);
				filterMatch = true;
			}
		}

		final String[] indices = new String[] { index };
		final String[] types = new String[] { type };

		final SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indices)
						/*.setSearchType(SearchType.DFS_QUERY_AND_FETCH)*/.setTypes(types);
		if (filterMatch) {
			queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, filterBuilder);
		} else if (boolMatch) {
			queryBuilder = boolQueryBuilder;
		} else {// FIXME
			queryBuilder = null;
		}
		// build score field
		final ScoreField scoreField = request.getScoreField();
		if (scoreField != null) {
			FieldValueFactorFunctionBuilder fvffb = new FieldValueFactorFunctionBuilder(scoreField.getName())
										.factor(scoreField.getFactor());
			if (StringUtil.isNotEmpty(scoreField.getModifier())) {
				final FieldValueFactorFunction.Modifier m = FieldValueFactorFunction.Modifier.valueOf(scoreField.getModifier());
				if (m != null) {
					fvffb = fvffb.modifier(m);
				}
			}
			queryBuilder = QueryBuilders.functionScoreQuery(queryBuilder).add(fvffb);
		}
		searchRequestBuilder.setQuery(queryBuilder);

		int page = request.getPage();
		int pageSize = request.getPageSize();
		if (page < 0) {
			page = config.getDefaultPage();
		}
		if (pageSize < 0) {
			pageSize = config.getDefaultPageSize();
		}

		searchRequestBuilder.setFrom(page * pageSize);
		searchRequestBuilder.setSize(pageSize);
		final Iterator<String> fields = factory.getResultClass(resultId).getFields().keySet().iterator();
		String field;
		while (fields.hasNext()) {
			field = fields.next();
			searchRequestBuilder.addField(field);
		}
		// add sort
		final List<SortField> sorts = request.getSortFields();
		if (!CollectionUtil.isEmpty(sorts)) {
			for (final SortField sort : sorts) {
				searchRequestBuilder.addSort(sort.getName(), sort.getOrder().esOrder());
			}
			searchRequestBuilder.addSort(new ScoreSortBuilder());
		}
		// add aggregation
		final List<AggregationDefinition> aggregations = request.getAggregations();
		AggregationBuilder<?> builder;
		for (final AggregationDefinition aggregation : aggregations) {
			builder = ReflectionUtil.invokeStaticMethod(AggregationBuilders.class, aggregation.getType(),
					new Class[] { String.class }, aggregation.getName());
			if (builder instanceof ValuesSourceAggregationBuilder) {
				((ValuesSourceAggregationBuilder<?>) builder).field(aggregation.getField());
			} else {
				// TODO
			}
			searchRequestBuilder.addAggregation(builder);
		}

		final SearchResponse response = searchRequestBuilder.execute().actionGet(config.getTimeoutMillis());
		final RealtimeSearchResponse result = new RealtimeSearchResponse();

		final long totalHits = response.getHits().totalHits();
		LOG.info("got total {} results", totalHits);
		result.setTotal(totalHits);
		result.setPage(page);

//		final List<Map<String, Object>> hits = Lists.newArrayList();
		for (final SearchHit hit : response.getHits()) {
			result.addHit(RealtimeSearchUtil.convert(hit.fields()));
//			hits.add(RealtimeSearchUtil.convert(hit.fields()));
//			it = factory.getResultClass(resultId).getFields().keySet().iterator();
//			results.add(RealtimeSearchUtil.cutSource(source, it));
//			source = hit.getSource();
		}

		MultiBucketsAggregation mbAggregation;
		for (final AggregationDefinition aggregation : aggregations) {
			mbAggregation = response.getAggregations().get(aggregation.getName());
			result.addAggregation(aggregation.getName(), mbAggregation.getBuckets());
		}

		return result;
	}
}
