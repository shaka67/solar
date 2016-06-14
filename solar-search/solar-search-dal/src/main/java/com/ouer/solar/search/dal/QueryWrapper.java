/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QueryWrapper  {

	private String table;
    protected long id;
    private int batchSize = 500;
    private int mod;
    private int baseMod;
    private int minId;
    private int maxId;
    
	public static QueryWrapper createQueryWrapper(String table, int batchSize, int mod,
			int baseMod) {
		final QueryWrapper wrapper = new QueryWrapper().withTable(table)
																.withMod(mod)
																.withBaseMod(baseMod)
																.withBatchSize(batchSize);
		return wrapper;
	}

	public String getTable() {
        return table;
    }

    public QueryWrapper withTable(String table) {
    	this.table = table;
        return this;
    }
    
	public Long getId() {
		return id;
	}

	public QueryWrapper withId(long id) {
		this.id = id;
		return this;
	}

    public int getBatchSize() {
		return batchSize;
	}

	public QueryWrapper withBatchSize(int batchSize) {
		this.batchSize = batchSize;
		return this;
	}

	public int getMod() {
		return mod;
	}

	public QueryWrapper withMod(int mod) {
		this.mod = mod;
		return this;
	}

	public int getBaseMod() {
		return baseMod;
	}

	public QueryWrapper withBaseMod(int baseMod) {
		this.baseMod = baseMod;
		return this;
	}

	public int getMinId() {
		return minId;
	}

	public QueryWrapper withMinId(int minId) {
		this.minId = minId;
		return this;
	}

	public int getMaxId() {
		return maxId;
	}

	public QueryWrapper withMaxId(int maxId) {
		this.maxId = maxId;
		return this;
	}


}
