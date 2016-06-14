/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.io.Serializable;
import java.util.List;

import com.ouer.solar.lang.StringableSupport;

/**
 * 代表一棵树
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午10:51:53
 * @param <K> 键类型
 * @param <E> 元素类型
 */

public class Tree<K extends Comparable<K>, P, C> extends StringableSupport implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7702618295837360370L;

    /**
     * 主键
     */
    protected K id;

    /**
     * 父主键
     */
    protected K parentId;

    /**
     * 父亲
     */
    protected P parent;

    /**
     * 子列表
     */
    protected List<C> children;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public K getParentId() {
        return parentId;
    }

    public void setParentId(K parentId) {
        this.parentId = parentId;
    }

    public P getParent() {
        return parent;
    }

    public void setParent(P parent) {
        this.parent = parent;
    }

    public List<C> getChildren() {
        return children;
    }

    public void setChildren(List<C> children) {
        this.children = children;
    }

}
