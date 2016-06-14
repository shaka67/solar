/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal;

import com.ouer.solar.able.Keyable;
import com.ouer.solar.search.dal.cursor.Cursor;
import com.ouer.solar.search.dal.op.InIDs;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DataOperation<T extends Keyable<Long>> extends Cursor<T>,
		InIDs<T> {

}
