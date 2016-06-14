/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.cursor;

import java.util.List;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Keyable;

/**
 * 游标操作
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月22日 上午2:02:25
 * @param <T>
 */

public class CursorOperation<K extends Comparable<K>, V extends Keyable<K>> implements DataCursor<V> {

    /**
     * 游标操作请求
     */
    protected CursorQuery<K, V> query = new CursorQuery<K, V>();

    /**
     * 当前操作总数，用来判断数据是否获取完毕
     */
    protected int total;

    /**
     * 当前最大ID值
     */
    private K maxId;

    private Cursor<K, V> cursor;

    public static <K extends Comparable<K>, V extends Keyable<K>> CursorOperation<K, V> create() {
        return new CursorOperation<K, V>();
    }

    @Override
    public boolean hasNext() {
        // 初始化当前最大ID值
        if (maxId == null) {
            maxId = cursor.maxIdByMod(query);
        }

        if (maxId != null && query.getId() == null) {
            return true;
        }

        return (maxId == null) ? false : maxId.compareTo(query.getId()) > 0;
    }

    @Override
    public List<V> next() {
        List<V> results = cursor.getDataList(query);

        if (CollectionUtil.isNotEmpty(results)) {
            // 更新当前ID值
            query.setId(results.get(results.size() - 1).getId());
            // 赋值同时更新处理总数
            total += results.size();
            return results;
        }
        // 更新当前ID值
        // request.setId(request.getId() + request.getBatchSize());
        return results;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count() {
        return total;
    }

    public CursorQuery<K, V> getQuery() {
        return query;
    }

    public void setQuery(CursorQuery<K, V> query) {
        this.query = query;
    }

    public void setCursor(Cursor<K, V> cursor) {
        this.cursor = cursor;
    }

}
