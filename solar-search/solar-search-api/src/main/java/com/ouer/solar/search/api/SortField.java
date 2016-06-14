/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

import java.io.Serializable;

import org.elasticsearch.search.sort.SortOrder;

import com.google.common.base.Objects;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SortField implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final String name;
	private final Order order;

	public SortField(String name, Order order) {
		this.name = name;
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public Order getOrder() {
		return order;
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
        final SortField another = (SortField) obj;
        return Objects.equal(name, another.name);
	}

	public enum Order {

	    ASC("asc", SortOrder.ASC),
	    DESC("desc", SortOrder.DESC)
	    ;

	    private final String type;
	    private final SortOrder es;

	    Order(String type, SortOrder es) {
	    	this.type = type;
	    	this.es = es;
	    }

		public String type() {
			return type;
		}

		public SortOrder esOrder() {
			return es;
		}

		public static Order fromType(String type) {
	        for (final Order order : Order.values()) {
	            if (order.type().equals(type)) {
	                return order;
	            }
	        }
	        return null;
	    }
	}
}
