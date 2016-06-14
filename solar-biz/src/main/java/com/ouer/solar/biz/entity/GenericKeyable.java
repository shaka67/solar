/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import com.ouer.solar.able.Keyable;

/**
 * 通用主键，用泛型表示
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * 
 * @version create on 2013年10月27日 下午3:49:18
 */

public abstract class GenericKeyable<T extends Keyable<T>> extends Entity<GenericKeyable<T>> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7597457608068150828L;

    /**
     * 主键
     */
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    /**
     * ID相同的2个对象，返回true
     */
    @Override
	protected boolean isEquals(GenericKeyable<T> obj) {
        return id.equals(obj.id);
    }

    @Override
	protected Object hashKey() {
        return id;
    }

}
