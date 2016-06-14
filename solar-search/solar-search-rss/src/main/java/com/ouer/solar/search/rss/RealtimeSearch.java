/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.rss;

import com.ouer.solar.search.api.CustomizedSearchRequest;
import com.ouer.solar.search.api.RealtimeSearchResponse;
import com.ouer.solar.search.api.RssException;
import com.ouer.solar.search.api.SimpleSearchRequest;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RealtimeSearch
{
	public RealtimeSearchResponse search(SimpleSearchRequest request) throws RssException;

	public RealtimeSearchResponse search(CustomizedSearchRequest request) throws RssException;
}
