/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class IndexField {

	private final String name;

	private String type;
    private IndexType indexType;
    private StoreType storeType;

	public IndexField(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}

}
