/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.ibs;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface IndexBuildService
{
	public void buildIndex() throws Exception;

	public void deleteIndex(String index) throws IbsException;

	public void buildMapping(int id) throws Exception;
}
