/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation.Bucket;

import com.google.common.collect.Maps;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RealtimeSearchResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long total;
	private int page;

	private final List<Map<String, Object>> hits = Lists.newArrayList();
	private final Map<String, List<Aggregation>> aggregations = Maps.newHashMap();

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return hits.size();
	}

	public List<Map<String, Object>> getHits() {
		return hits;
	}

	public void addHit(Map<String, Object> hit) {
		hits.add(hit);
	}

	public Map<String, List<Aggregation>> getAggregations() {
		return aggregations;
	}

	public void addAggregation(String name, Collection<? extends Bucket> buckets) {
		final List<Aggregation> list = Lists.newArrayList();
		for (final Bucket bucket : buckets) {
			list.add(new Aggregation(bucket.getKey(), bucket.getDocCount()));
		}
		aggregations.put(name, list);
	}

	public List<Aggregation> getAggregation(String name) {
		return aggregations.get(name);
	}

	@Override
	public String toString() {
		return "RealtimeSearchResponse [total=" + total + ", page=" + page
				+ ", hits=" + hits + ", aggregations=" + aggregations + "]";
	}

	public static class Aggregation implements Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		private final String key;
		private final long docCount;

		public Aggregation(String key, long docCount) {
			this.key = key;
			this.docCount = docCount;
		}

		public String getKey() {
			return key;
		}

		public long getDocCount() {
			return docCount;
		}

	}

}
