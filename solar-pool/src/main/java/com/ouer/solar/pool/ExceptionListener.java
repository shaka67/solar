/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ExceptionListener<T> 
{
	public void onException(List<T> records);
}
