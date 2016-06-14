/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.cursor;

import java.util.List;

import com.ouer.solar.able.Keyable;

/**
 * 游标接口，可递归获取数据
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月22日 上午1:58:44
 * @param <T>
 */

public interface Cursor<K extends Comparable<K>, V extends Keyable<K>> {

    /**
     * 获取所有数据
     * 
     * @return 数据列表
     */
    List<V> getAll();

    /**
     * 通过请求<code>request</code>获取数据列表
     * 
     * @param query 请求对象 @see CursorQuery
     * @return 数据列表
     */
    List<V> getDataList(CursorQuery<K, V> query);

    /**
     * 通过唯一标志符获取数据
     * 
     * @param id 唯一标志符
     * @return 数据
     */
    V getData(K id);

    /**
     * 获取数据总量
     * 
     * @return 数据总量
     */
    int count();

    /**
     * 通过请求<code>query</code>获取数据最大标志符
     * 
     * @param query 请求对象 @see CursorQuery
     * @return 数据最大标志符
     */
    K maxIdByMod(CursorQuery<K, V> query);

}
