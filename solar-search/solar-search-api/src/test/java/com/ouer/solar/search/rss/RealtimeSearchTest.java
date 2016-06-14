/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.search.api.AggregationDefinition;
import com.ouer.solar.search.api.BoolType;
import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.FilterField;
import com.ouer.solar.search.api.FilterFieldAndValue;
import com.ouer.solar.search.api.FilterType;
import com.ouer.solar.search.api.QueryType;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.SearchApi;
import com.ouer.solar.search.api.SearchField;
import com.ouer.solar.search.api.SearchFieldAndValue;
import com.ouer.solar.search.api.SearchRequestException;
import com.ouer.solar.search.api.SimpleSearchRequest;
import com.ouer.solar.search.api.SortField;
import com.ouer.solar.search.api.SortField.Order;
import com.ouer.solar.search.api.spring.SearchApiSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("prod")
@ContextConfiguration(classes = { SearchApiSpringConfig.class, ProfileSpringConfig.class })
public class RealtimeSearchTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SearchApi api;

	@Test
	public void testSimpleSearch() {
//		Map<String, Object> params = Maps.newHashMap();
//		params.put("name", "全文搜索");
//		params.put("subName", "全文搜索");
//		SimpleSearchRequest request = new SimpleSearchRequest("indra");
//		request.setResultId(1);
//		request.setSearchFieldsId(1);
//		request.setSearchParams(params);
//		request.setAggregationsId(1);
//		RealtimeSearchResponse result = api.search(request);
//		System.out.println(result.getAggregation("categories-term"));

		final Map<String, Object> params = Maps.newHashMap();
		params.put("name", "正宗");
//		params.put("subName", "外套");
		final SimpleSearchRequest request = new SimpleSearchRequest("c2c");
		request.setResultId(1);
		request.setSearchFieldsId(1);
		request.setSearchParams(params);
		request.setAggregationsId(1);
		final RealtimeSearchResponse result = api.search(request);
		System.out.println(result.getAggregation("categories-term"));
	}

	@Test
	public void testSimpleSearchCollection() {
		Map<String, Object> params = Maps.newHashMap();
		final List<String> categories = Lists.newArrayList();
		categories.add("test");
		categories.add("运动鞋");
		params.put("categories", categories);
		final SimpleSearchRequest request = new SimpleSearchRequest("indra");
		request.setResultId(1);
		request.setSearchFieldsId(2);
		request.setSearchParams(params);
		request.setSortsId(1);
		request.setAggregationsId(2);

		params = Maps.newHashMap();
		params.put("domain", 36);
		request.setFilterFieldsId(1);
		request.setFilterParams(params);

		final RealtimeSearchResponse result = api.search(request);
		System.err.println(result.getAggregation("status-term"));
	}

	@Test
	public void testCustomizedSearch() throws SearchRequestException {
		final CustomizedSearchRequest request = new CustomizedSearchRequest("indra", 1);
		request.addField(new SearchFieldAndValue(
				new SearchField("name", BoolType.SHOULD, QueryType.MATCH, 3.0f, BoolType.MUST), "全文搜索"));
		request.addField(new SearchFieldAndValue(
				new SearchField("subName", BoolType.SHOULD, QueryType.MATCH, 3.0f, BoolType.MUST), "全文搜索"));
		request.addAggregation(new AggregationDefinition("terms", "categories-term", "categories"));
		request.addAggregation(new AggregationDefinition("terms", "tags-term", "tags"));
		request.addAggregation(new AggregationDefinition("terms", "attributes-term", "attributes"));

		request.addFilter(new FilterFieldAndValue(new FilterField("domain", FilterType.TERM), 36));

		final RealtimeSearchResponse result = api.search(request);
		System.err.println(result.getAggregation("categories-term"));
	}

	@Test
	public void testCustomizedSearchCollection() throws SearchRequestException {
		final CustomizedSearchRequest request = new CustomizedSearchRequest("indra", 1);
		final List<String> categories = Lists.newArrayList();
		categories.add("test");
		categories.add("运动鞋");
		request.addField(new SearchFieldAndValue(
				new SearchField("categories", BoolType.MUST, QueryType.TERM, 1.0f, BoolType.SHOULD), categories));
		request.addSort(new SortField("price", Order.DESC));
		request.addAggregation(new AggregationDefinition("terms", "status-term", "status"));

		request.addFilter(new FilterFieldAndValue(new FilterField("domain", FilterType.TERM), 36));

		final RealtimeSearchResponse result = api.search(request);
		System.err.println(result.getAggregation("status-term"));
	}
}
