/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CacheObject {

	private String namespace;
	private Object key;
	private Object value;
	private CacheLevel level;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public CacheLevel getLevel() {
		return level;
	}

	public void setLevel(CacheLevel level) {
		this.level = level;
	}

}
