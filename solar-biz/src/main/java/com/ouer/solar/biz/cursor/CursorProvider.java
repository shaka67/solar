/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.cursor;

import com.ouer.solar.able.Keyable;

/**
 * 游标提供
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-2-9 上午12:58:02
 */

public interface CursorProvider<K extends Comparable<K>, V extends Keyable<K>> {

    /**
     * 获取游标 @see Cursor
     * 
     * @return 游标
     */
    Cursor<K, V> getCursor();

}
