/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

/**
 * 基于<code>long</code>的ID
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午8:27:38
 */

public class LongID extends BaseEntity<Long, LongID> implements Comparable<LongID> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6636605543040554949L;

    /**
     * ID相同的2个对象，返回true
     */
    @Override
	protected boolean isEquals(LongID obj) {
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
	public int compareTo(final LongID other) {
        return (id < other.id ? -1 : (id == other.id ? 0 : 1));
    }

}
