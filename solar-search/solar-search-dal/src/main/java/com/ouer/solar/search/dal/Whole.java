/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal;

import java.util.List;

import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.dal.op.InIDs;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Whole<T extends Keyable<Long>> extends InIDs<T>
{
	public List<T> getWhole();

	public int count();
}
