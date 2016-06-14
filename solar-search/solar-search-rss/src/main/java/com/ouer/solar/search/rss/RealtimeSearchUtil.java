/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.reflect.MethodUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsFilterBuilder;
import org.elasticsearch.search.SearchHitField;

import com.google.common.collect.Maps;
import com.ouer.solar.search.api.BoolType;
import com.ouer.solar.search.api.FilterType;
import com.ouer.solar.search.api.QueryType;
import com.ouer.solar.search.api.RssException;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class RealtimeSearchUtil {

	//	public static QueryBuilder fetchBuilder(QueryType queryType, String key, Collection<Object> values) {
	//	if (values.size() == 1) {
	//		return fetchBuilder(queryType, key, values.iterator().next());
	//	}
	//
	//	final BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
	//	QueryBuilder queryBuilder;
	//	if (QueryType.MATCH.equals(queryType)) {
	//		for (final Object value : values) {
	//			queryBuilder = QueryBuilders.matchQuery(key, value);
	//			boolQueryBuilder.should(queryBuilder);
	//		}
	//	}
	//	else if (QueryType.TERM.equals(queryType)) {
	//		for (final Object value : values) {
	//			queryBuilder = QueryBuilders.termsQuery(key, value);
	//			boolQueryBuilder.should(queryBuilder);
	//		}
	//	}
	//	else if (QueryType.PHRASE.equals(queryType)) {
	//		for (final Object value : values) {
	//			queryBuilder = QueryBuilders.matchPhraseQuery(key, value);
	//			boolQueryBuilder.should(queryBuilder);
	//		}
	//	}
	//	// TODO range
	//
	//	return boolQueryBuilder;
	//}

	public static QueryBuilder fetchBuilder(QueryType queryType, String key, Object value, BoolType multiType) {
		if (value instanceof Collection) {
			final Collection<Object> values = (Collection<Object>) value;
			final BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			QueryBuilder queryBuilder;
			if (QueryType.MATCH.equals(queryType)) {
				for (final Object v : values) {
					queryBuilder = QueryBuilders.matchQuery(key, v);
//					boolQueryBuilder.should(queryBuilder);
					try {
						final Method method = MethodUtils.getAccessibleMethod(boolQueryBuilder.getClass(),
								multiType.desc(), QueryBuilder.class);
						method.invoke(boolQueryBuilder, queryBuilder);
					} catch (final Exception e) {
						throw new RssException(e);
					}
				}
			}
			else if (QueryType.TERM.equals(queryType)) {
				for (final Object v : values) {
					queryBuilder = QueryBuilders.termsQuery(key, v);
//					boolQueryBuilder.should(queryBuilder);
					try {
						final Method method = MethodUtils.getAccessibleMethod(boolQueryBuilder.getClass(),
								multiType.desc(), QueryBuilder.class);
						method.invoke(boolQueryBuilder, queryBuilder);
					} catch (final Exception e) {
						throw new RssException(e);
					}
				}
			}
			else if (QueryType.PHRASE.equals(queryType)) {
				for (final Object v : values) {
					queryBuilder = QueryBuilders.matchPhraseQuery(key, v);
//					boolQueryBuilder.should(queryBuilder);
					try {
						final Method method = MethodUtils.getAccessibleMethod(boolQueryBuilder.getClass(),
								multiType.desc(), QueryBuilder.class);
						method.invoke(boolQueryBuilder, queryBuilder);
					} catch (final Exception e) {
						throw new RssException(e);
					}
				}
			}
			// TODO range

			return boolQueryBuilder;
		}

		if (QueryType.MATCH.equals(queryType)) {
			return QueryBuilders.matchQuery(key, value);
		}
		if (QueryType.TERM.equals(queryType)) {
			return QueryBuilders.termsQuery(key, value);
		}
		if (QueryType.PHRASE.equals(queryType)) {
			return QueryBuilders.matchPhraseQuery(key, value);
		}
		if (QueryType.RANGE.equals(queryType)) {
	//		final RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery(key);
	//		// parse value
	//		boolean containLower = false;
	//		boolean containUpper = false;
	//		boolean includeLower = false;
	//		boolean includeUpper = false;
	//		if (value.startsWith(StringPool.Symbol.LEFT_BRACE)) {
	//			includeLower = false;
	//			containLower = true;
	//		} else if (value.startsWith(StringPool.Symbol.LEFT_SQ_BRACKET)) {
	//			includeLower = true;
	//			containLower = true;
	//		}
	//		if (value.endsWith(StringPool.Symbol.RIGHT_BRACE)) {
	//			includeUpper = false;
	//			containUpper = true;
	//		} else if (value.endsWith(StringPool.Symbol.RIGHT_SQ_BRACKET)) {
	//			includeUpper = true;
	//			containUpper = true;
	//		}
	//
	//		if (containLower && containUpper) {
	//			final String content = value.substring(1, value.length() - 1);
	//			final String lower = content.split(StringPool.Symbol.DASH)[0].trim();
	//			final String upper = content.split(StringPool.Symbol.DASH)[1].trim();
	//			final double lowerValue = Double.valueOf(lower);
	//			final double upperValue = Double.valueOf(upper);
	//			queryBuilder.includeLower(includeLower).includeUpper(includeUpper).from(lowerValue).to(upperValue);
	//			return queryBuilder;
	//		}
	//		if (containLower) {
	//			final String lower = value.substring(1).trim();
	//			final double lowerValue = Double.valueOf(lower);
	//			if (includeLower) {
	//				queryBuilder.gte(lowerValue);
	//			} else {
	//				queryBuilder.gt(lowerValue);
	//			}
	//			return queryBuilder;
	//		}
	//		if (containUpper) {
	//			final String upper = value.substring(0, value.length() - 1).trim();
	//			final double upperValue = Double.valueOf(upper);
	//			if (includeUpper) {
	//				queryBuilder.lte(upperValue);
	//			} else {
	//				queryBuilder.lt(upperValue);
	//			}
	//			return queryBuilder;
	//		}
		}

		return null;
	}

	public static FilterBuilder fetchBuilder(FilterType filterType, String key, Object value) {
		if (FilterType.TERM.equals(filterType)) {
			return FilterBuilders.termFilter(key, value);
		}
		if (FilterType.IN.equals(filterType)) {
			if (value instanceof Iterable) {
				return new TermsFilterBuilder(key, (Iterable<?>) value);
			}
			return FilterBuilders.inFilter(key, value);
		}
		if (FilterType.RANGE.equals(filterType)) {
			// TODO
		}

		return null;
	}

	public static Map<String, Object> cutSource(Map<String, Object> source, Iterator<String> fields) {
		final Map<String, Object> result = Maps.newHashMap();
		String field;
		Object value;
		while (fields.hasNext()) {
			field = fields.next();
			value = source.get(field);
			result.put(field, value);
		}
		return result;
	}

	public static Map<String, Object> convert(Map<String, SearchHitField> fields) {
		final Map<String, Object> result = Maps.newHashMap();
		final Iterator<String> it = fields.keySet().iterator();
		String key;
		Object value;
		SearchHitField field;
		while (it.hasNext()) {
			key = it.next();
			field = fields.get(key);
			value = field.getValues();
			result.put(key, value);
		}
		return result;
	}
}
