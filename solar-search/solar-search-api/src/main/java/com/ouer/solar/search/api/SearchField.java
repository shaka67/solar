/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchField implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String name;
	private final BoolType boolType;
	private final QueryType queryType;
	private final float boost;
	private final BoolType multiType;
	private final boolean constantScore;

	public SearchField(String name,
			BoolType boolType,
			QueryType queryType,
			float boost,
			BoolType multiType) {
		this(name, boolType, queryType, boost, multiType, false);
	}

	public SearchField(String name,
						BoolType boolType,
						QueryType queryType,
						float boost,
						BoolType multiType,
						boolean constantScore) {
		this.name = name;
		this.boolType = boolType;
		this.queryType = queryType;
		this.boost = boost;
		this.multiType = multiType;
		this.constantScore = constantScore;
	}

	public String getName() {
		return name;
	}

	public BoolType getBoolType() {
		return boolType;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public float getBoost() {
		return boost;
	}

	public BoolType getMultiType() {
		return multiType;
	}

	public boolean isConstantScore() {
		return constantScore;
	}

}
