/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.List;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Iteration;

/**
 * 基于<code>List</code>实现的<code>Iterator
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-6-16 下午8:15:50
 */
public class ListIteration<T> implements Iteration<T> {

    /**
     * 数据列表
     */
    protected List<T> list;

    /**
     * 当前数据源列表索引
     */
    protected int index;

    /**
     * 当前数据
     */
    protected T currentData;

    public ListIteration() {
        list = CollectionUtil.createArrayList();
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public T next() {
        currentData = list.get(index);
        index++;

        return currentData;
    }

    @Override
    public void reset() {
        index = 0;
    }

}
