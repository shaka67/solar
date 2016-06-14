/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CacheExpiredSignal {

	private final String namespace;
	private final Object key;

	public CacheExpiredSignal(String namespace, Object key) {
		this.namespace = namespace;
		this.key = key;
	}

	public String getNamespace() {
		return namespace;
	}

	public Object getKey() {
		return key;
	}

}
