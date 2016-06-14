/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

/**
 * 基于<code>int</code>的ID
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午8:27:09
 */

public class ID extends BaseEntity<Integer, ID> implements Comparable<ID> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2632249062976595824L;

    /**
     * ID相同的2个对象，返回true
     */
    @Override
	protected boolean isEquals(ID obj) {
        return id.equals(obj.id);
    }

    @Override
	protected Object hashKey() {
        return id;
    }

    /**
     * 比较主键值大小
     */
    @Override
	public int compareTo(final ID other) {
        return (id < other.id ? -1 : (id == other.id ? 0 : 1));
    }

}
