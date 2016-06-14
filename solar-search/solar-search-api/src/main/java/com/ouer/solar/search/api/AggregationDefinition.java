/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class AggregationDefinition implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String type;
	private final String name;
	private final String field;

	public AggregationDefinition(String type, String name, String field) {
		this.type = type;
		this.name = name;
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getField() {
		return field;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        final AggregationDefinition another = (AggregationDefinition) obj;
        return Objects.equal(name, another.name);
	}

}
