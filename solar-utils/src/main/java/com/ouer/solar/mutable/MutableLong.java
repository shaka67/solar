/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mutable;

import com.ouer.solar.ClassUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午4:28:29
 */
public final class MutableLong extends Number implements Comparable<MutableLong>, Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5118478602649863710L;

    public MutableLong() {
    }

    public MutableLong(long value) {
        this.value = value;
    }

    public MutableLong(String value) {
        this.value = Long.parseLong(value);
    }

    public MutableLong(Number number) {
        this.value = number.longValue();
    }

    // ---------------------------------------------------------------- value

    /**
     * The mutable value.
     */
    public long value;

    /**
     * Returns mutable value.
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets mutable value.
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Sets mutable value from a Number.
     */
    public void setValue(Number value) {
        this.value = value.longValue();
    }

    // ---------------------------------------------------------------- object

    /**
     * Stringify the value.
     */
    @Override
    public String toString() {
        return Long.toString(value);
    }

    /**
     * Returns a hashcode for this value.
     */
    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    /**
     * Compares this object to the specified object.
     * 
     * @param obj the object to compare with.
     * @return <code>true</code> if the objects are the same; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (ClassUtil.isInstance(Long.class, obj)) {
                return obj.equals(value);
            }
            if (ClassUtil.isInstance(MutableLong.class, obj)) {
                return value == ((MutableLong) obj).value;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- number

    /**
     * Returns the value as a int.
     */
    @Override
    public int intValue() {
        return (int) value;
    }

    /**
     * Returns the value as a long.
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * Returns the value as a float.
     */
    @Override
    public float floatValue() {
        return value;
    }

    /**
     * Returns the value as a double.
     */
    @Override
    public double doubleValue() {
        return value;
    }

    // ---------------------------------------------------------------- compare

    /**
     * Compares value of two same instances.
     */
    @Override
	public int compareTo(MutableLong other) {
        return value < other.value ? -1 : (value == other.value ? 0 : 1);
    }

    // ---------------------------------------------------------------- clone

    /**
     * Clones object.
     */
    @Override
    public MutableLong clone() {
        return new MutableLong(value);
    }

}