/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.cursor;

import java.util.Iterator;
import java.util.List;

import com.ouer.solar.able.Statable;

/**
 * 游标操作接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月22日 上午2:02:16
 * @param <T>
 */

public interface DataCursor<T> extends Iterator<List<T>>, Statable {

}