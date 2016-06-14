/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface IBatchExecutor<T> 
{
	public void execute(List<T> records);
}
