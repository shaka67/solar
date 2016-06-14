/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.search.api.AggregationDefinition;
import com.ouer.solar.search.api.BoolType;
import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.QueryType;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.RssException;
import com.ouer.solar.search.api.SearchField;
import com.ouer.solar.search.api.SearchFieldAndValue;
import com.ouer.solar.search.api.SearchRequestException;
import com.ouer.solar.search.api.SimpleSearchRequest;
import com.ouer.solar.search.api.SortField;
import com.ouer.solar.search.api.SortField.Order;
import com.ouer.solar.search.rss.config.RssSpringConfig;
import com.ouer.solar.search.rss.dynamic.RealtimeSearchManager;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { RssSpringConfig.class, ProfileSpringConfig.class })
public class RealtimeSearchTest extends AbstractJUnit4SpringContextTests {

//	@Autowired
//	RealtimeSearch proxy;

	@Autowired
	RealtimeSearchManager manager;

//	@Test
//	public void testBase() throws RssException {
//		final Map<String, String> params = Maps.newHashMap();
////		params.put(RssContants.PARAM_INDEX_ID_KEY, "1");
////		params.put(RssContants.PARAM_RESULT_ID_KEY, "1");
//		params.put(RssContants.PARAM_SEARCH_ID_KEY, "1");
//		params.put("name", "全文搜索");
//		params.put("subName", "全文搜索");
//		final RealtimeSearchResponse result = proxy.search(1000, params);
//		System.out.println(result.getAggregation("categories-term"));
//	}
//
//	@Test
//	public void testCollectioinSearch() throws RssException {
//		final Map<String, String> params = Maps.newHashMap();
////		params.put(RssContants.PARAM_INDEX_ID_KEY, "1");
////		params.put(RssContants.PARAM_RESULT_ID_KEY, "1");
//		params.put(RssContants.PARAM_SEARCH_ID_KEY, "3");
//		params.put("categories", "test");
//		final RealtimeSearchResponse result = proxy.search(1000, params);
//		System.out.println(result.getAggregation("status-term"));
//	}

	@Test
	public void testSimpleSearch() throws RssException {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("name", "全文搜索");
		params.put("subName", "全文搜索");
		final SimpleSearchRequest request = new SimpleSearchRequest("indra");
		request.setResultId(1);
		request.setSearchFieldsId(1);
		request.setSearchParams(params);
		request.setAggregationsId(1);
		final RealtimeSearchResponse result = manager.search(request);
		System.out.println(result.getAggregation("categories-term"));
	}

	@Test
	public void testSimpleSearchCollection() throws RssException {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("categories", "test");
		final SimpleSearchRequest request = new SimpleSearchRequest("indra");
		request.setResultId(1);
		request.setSearchFieldsId(2);
		request.setSearchParams(params);
		request.setSortsId(1);
		request.setAggregationsId(2);
		final RealtimeSearchResponse result = manager.search(request);
		System.out.println(result.getAggregation("status-term"));
	}

	@Test
	public void testCustomizedSearch() throws RssException, SearchRequestException {
		final CustomizedSearchRequest request = new CustomizedSearchRequest("indra", 1);
		request.addField(new SearchFieldAndValue(
				new SearchField("name", BoolType.SHOULD, QueryType.MATCH, 3.0f, BoolType.MUST), "全文搜索"));
		request.addField(new SearchFieldAndValue(
				new SearchField("subName", BoolType.SHOULD, QueryType.MATCH, 3.0f, BoolType.MUST), "全文搜索"));
		request.addAggregation(new AggregationDefinition("terms", "categories-term", "categories"));
		request.addAggregation(new AggregationDefinition("terms", "tags-term", "tags"));
		request.addAggregation(new AggregationDefinition("terms", "attributes-term", "attributes"));
		final RealtimeSearchResponse result = manager.search(request);
		System.out.println(result.getAggregation("categories-term"));
	}

	@Test
	public void testCustomizedSearchCollection() throws RssException, SearchRequestException {
		final CustomizedSearchRequest request = new CustomizedSearchRequest("indra", 1);
		request.addField(new SearchFieldAndValue(
				new SearchField("categories", BoolType.MUST, QueryType.TERM, 1.0f, BoolType.SHOULD), "test"));
		request.addSort(new SortField("price", Order.DESC));
		request.addAggregation(new AggregationDefinition("terms", "status-term", "status"));
		final RealtimeSearchResponse result = manager.search(request);
		System.out.println(result.getAggregation("status-term"));
	}
}
