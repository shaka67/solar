/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FilterFieldAndValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final FilterField filter;
	private final Object value;

	public FilterFieldAndValue(FilterField filter, Object value) {
		this.filter = filter;
		this.value = value;
	}

	public FilterField getFilter() {
		return filter;
	}

	public Object getValue() {
		return value;
	}
}
