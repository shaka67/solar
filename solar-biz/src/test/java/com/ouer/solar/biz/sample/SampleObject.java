/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.sample;

import java.util.Arrays;

import com.ouer.solar.biz.entity.Entity;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午3:59:16
 */

public class SampleObject extends Entity<SampleObject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5146414906617735277L;

	private String stringField;

	private int intField;

	private double doubleField;

	private byte[] bytesField;

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public int getIntField() {
		return intField;
	}

	public void setIntField(int intField) {
		this.intField = intField;
	}

	public double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public byte[] getBytesField() {
		return bytesField;
	}

	public void setBytesField(byte[] bytesField) {
		this.bytesField = bytesField;
	}

	@Override
	protected boolean isEquals(SampleObject obj) {
		return stringField.equals(obj.stringField)
				&& Arrays.equals(bytesField, obj.bytesField);
	}

	@Override
	protected Object hashKey() {
		return stringField + Arrays.toString(bytesField);
	}

}
