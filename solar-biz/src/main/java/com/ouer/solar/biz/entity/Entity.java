/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import java.io.Serializable;

import com.ouer.solar.able.CloneableObject;
import com.ouer.solar.lang.StringableSupport;

/**
 * 实体对象基类，提供通用方法
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午8:27:00
 */

public abstract class Entity<T extends Entity<T>> extends StringableSupport implements Serializable, CloneableObject<T> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3365154083759739647L;

    @Override
    public T clone() throws CloneNotSupportedException {
        @SuppressWarnings("unchecked")
        T clone = (T) super.clone();
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 到此处类型必相同
        @SuppressWarnings("unchecked")
        T other = (T) obj;
        return isEquals(other);
    }

    /**
     * 比较是否相等，改变 {@link #equals(Object)}只需重新此方法
     * 
     * @param obj 被比较的对象
     * @return 如果相等返回<code>true</code>，否则返回<code>false</code>
     */
    protected abstract boolean isEquals(T obj);

    /**
     * 根据<code>HashMap</code>的存储结构，如果 <code>A.equals(B)</code>，则必有 <code>A.hashCode()==B.hashCode()</code>
     */
    @Override
    public int hashCode() {
        if (hashKey() == null) {
            // Object的hashCode
            return super.hashCode();
        }

        final int prime = 31;
        int result = 1;
        result = prime * result + hashKey().hashCode();
        return result;
    }

    /**
     * 返回计算{@link #hashCode()}的关键字，强制重写，确保如果 <code>A.equals(B)</code>，则必有 <code>A.hashCode()==B.hashCode()</code>
     * 
     * @return 计算{@link #hashCode()}的关键字
     */
    protected abstract Object hashKey();

}
