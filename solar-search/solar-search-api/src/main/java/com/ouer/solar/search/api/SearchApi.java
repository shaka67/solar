/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface SearchApi {

	public RealtimeSearchResponse search(SimpleSearchRequest request);

	public RealtimeSearchResponse search(CustomizedSearchRequest request);
}
