/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchFieldAndValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final SearchField field;
	private final Object value;

	public SearchFieldAndValue(SearchField field, Object value) {
		this.field = field;
		this.value = value;
	}

	public SearchField getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}
}
