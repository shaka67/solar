/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface IRecordPool<T> {

	public boolean add(T rec);

	public List<T> asList();

	public List<T> getWholeRecords();

	public int remainCapacity();

	public int size();

	public void setBatchSize(int batchSize);

}
