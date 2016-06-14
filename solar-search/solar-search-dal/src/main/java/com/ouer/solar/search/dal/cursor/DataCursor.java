/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.dal.cursor;

import java.util.Iterator;
import java.util.List;

import com.ouer.solar.able.Statable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DataCursor<T> extends Iterator<List<T>>, Statable {

}