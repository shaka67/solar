/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SearchConfigAdmin {

	public boolean write(SearchConfig config);

	public boolean remove(String appId);
}
