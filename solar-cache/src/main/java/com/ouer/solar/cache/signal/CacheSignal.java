/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CacheSignal {

	private final int src;
	private final CacheSignalOperator operator;
	private final String namespace;
	private final Object key;

	CacheSignal(int src, CacheSignalOperator operator, String namespace, Object key) {
		this.src = src;
		this.operator = operator;
		this.namespace = namespace;
		this.key = key;
	}

	public int getSrc() {
		return src;
	}

	public CacheSignalOperator getOperator() {
		return operator;
	}

	public String getNamespace() {
		return namespace;
	}

	public Object getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "CacheCommand [src=" + src + ", operator=" + operator
				+ ", namespace=" + namespace + ", key=" + key + "]";
	}
}
