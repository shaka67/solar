/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.api;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface IncrementUpdateApi
{
	public void update(IncrementUpdateRequest request);

	public void updateField(IncrementUpdateFieldRequest request);
}
