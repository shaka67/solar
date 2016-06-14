/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sample;

import java.util.Arrays;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午8:37:42
 */
public class SampleSignal {

    private int intField;

    private byte byteField;

    private String stringField = "";

    private byte[] byteArrayField = new byte[0];

    private short shortField;

    private long longField;

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public short getShortField() {
        return shortField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public byte getByteField() {
        return byteField;
    }

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public byte[] getByteArrayField() {
        return byteArrayField;
    }

    public void setByteArrayField(byte[] byteArrayField) {
        this.byteArrayField = byteArrayField;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("intField:").append(intField).append(",");
        builder.append("byteField:").append(byteField).append(",");
        builder.append("stringField:").append(stringField).append(",");
        builder.append("byteArrayField:").append(Arrays.toString(byteArrayField)).append(",");
        builder.append("shortField:").append(shortField).append(",");
        builder.append("longField:").append(longField).append(",");
        return builder.toString();
        // return ToStringBuilder.reflectionToString(this,
        // ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
	public int hashCode() {
        return toString().hashCode();
    }

    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return this.toString().equals(obj.toString());
    }

}
