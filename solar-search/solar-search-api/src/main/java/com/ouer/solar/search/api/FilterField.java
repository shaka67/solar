/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FilterField implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String name;
	private final FilterType filterType;

	public FilterField(String name, FilterType filterType) {
		this.name = name;
		this.filterType = filterType;
	}

	public String getName() {
		return name;
	}

	public FilterType getFilterType() {
		return filterType;
	}

}
