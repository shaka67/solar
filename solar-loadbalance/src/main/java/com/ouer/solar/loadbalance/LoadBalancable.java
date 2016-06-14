/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.loadbalance;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface LoadBalancable<T> 
{
	T next();
}
