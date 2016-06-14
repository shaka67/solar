/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.Arrays;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.able.ArrayConvertable;
import com.ouer.solar.able.Lengthable;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月20日 上午2:19:17
 */
public enum PrimitiveArrayType implements ArrayConvertable, Lengthable {

    BOOLEAN {
        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == boolean[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return (boolean[]) value;
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.booleanToByte((boolean[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.booleanToChar((boolean[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.booleanToDouble((boolean[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.booleanToFloat((boolean[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.booleanToInt((boolean[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.booleanToLong((boolean[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.booleanToShort((boolean[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.booleanToString((boolean[]) value);
        }

        @Override
        public int length(Object value) {
            return ((boolean[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((boolean[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            boolean[] booleanArray = (boolean[]) value;
            int length = booleanArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(booleanArray[i]);
            }
        }
    },
    BYTE {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == byte[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.byteToBoolean((byte[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return (byte[]) value;
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.byteToChar((byte[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.byteToDouble((byte[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.byteToFloat((byte[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.byteToInt((byte[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.byteToLong((byte[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.byteToShort((byte[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.byteToString((byte[]) value);
        }

        @Override
        public int length(Object value) {
            return ((byte[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((byte[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            byte[] charArray = (byte[]) value;
            int length = charArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(charArray[i]);
            }
        }
    },
    CHAR {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == char[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.charToBoolean((char[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.charToByte((char[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return (char[]) value;
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.charToDouble((char[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.charToFloat((char[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.charToInt((char[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.charToLong((char[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.charToShort((char[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.charToString((char[]) value);
        }

        @Override
        public int length(Object value) {
            return ((char[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((char[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            char[] charArray = (char[]) value;
            int length = charArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(charArray[i]);
            }
        }
    },

    DOUBLE {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == double[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.doubleToBoolean((double[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.doubleToByte((double[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.doubleToChar((double[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return (double[]) value;
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.doubleToFloat((double[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.doubleToInt((double[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.doubleToLong((double[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.doubleToShort((double[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.doubleToString((double[]) value);
        }

        @Override
        public int length(Object value) {
            return ((double[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((double[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            double[] doubleArray = (double[]) value;
            int length = doubleArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(doubleArray[i]);
            }
        }
    },
    FLOAT {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == float[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.floatToBoolean((float[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.floatToByte((float[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.floatToChar((float[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.floatToDouble((float[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return (float[]) value;
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.floatToInt((float[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.floatToLong((float[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.floatToShort((float[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.floatToString((float[]) value);
        }

        @Override
        public int length(Object value) {
            return ((float[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((float[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            float[] floatArray = (float[]) value;
            int length = floatArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(floatArray[i]);
            }
        }
    },
    INT {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == int[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.intToBoolean((int[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.intToByte((int[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.intToChar((int[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.intToDouble((int[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.intToFloat((int[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return (int[]) value;
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.intToLong((int[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.intToShort((int[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.intToString((int[]) value);
        }

        @Override
        public int length(Object value) {
            return ((int[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((int[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            int[] intArray = (int[]) value;
            int length = intArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(intArray[i]);
            }

        }
    },
    LONG {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == long[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.longToBoolean((long[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.longToByte((long[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.longToChar((long[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.longToDouble((long[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.longToFloat((long[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.longToInt((long[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return (long[]) value;
        }

        @Override
        public short[] convert2Short(Object value) {
            return ArrayUtil.longToShort((long[]) value);
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.longToString((long[]) value);
        }

        @Override
        public int length(Object value) {
            return ((long[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((long[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            long[] longArray = (long[]) value;
            int length = longArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(longArray[i]);
            }
        }
    },
    SHORT {

        @Override
        public boolean hit(Class<?> clazz) {
            return clazz == short[].class;
        }

        @Override
        public boolean[] convert2Boolean(Object value) {
            return ArrayUtil.shortToBoolean((short[]) value);
        }

        @Override
        public byte[] convert2Byte(Object value) {
            return ArrayUtil.shortToByte((short[]) value);
        }

        @Override
        public char[] convert2Char(Object value) {
            return ArrayUtil.shortToChar((short[]) value);
        }

        @Override
        public double[] convert2Double(Object value) {
            return ArrayUtil.shortToDouble((short[]) value);
        }

        @Override
        public float[] convert2Float(Object value) {
            return ArrayUtil.shortToFloat((short[]) value);
        }

        @Override
        public int[] convert2Int(Object value) {
            return ArrayUtil.shortToInt((short[]) value);
        }

        @Override
        public long[] convert2Long(Object value) {
            return ArrayUtil.shortToLong((short[]) value);
        }

        @Override
        public short[] convert2Short(Object value) {
            return (short[]) value;
        }

        @Override
        public String[] convert2String(Object value) {
            return ArrayUtil.shortToString((short[]) value);
        }

        @Override
        public int length(Object value) {
            return ((short[]) value).length;
        }

        @Override
        public String toString(Object value) {
            return Arrays.toString((short[]) value);
        }

        @Override
        public void append(StringBuilder builder, Object value) {
            short[] shortArray = (short[]) value;
            int length = shortArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(shortArray[i]);
            }
        }
    };

    /**
     * 判断是否为<code>clazz</code>类型
     * 
     * @param clazz 指定的类型
     * @return 如果命中则返回<code>true</code>
     */
    public abstract boolean hit(Class<?> clazz);

    /**
     * 打印<code>value</code>字面表示
     * 
     * @param value 给定对象
     * @return 字面表示
     */
    public abstract String toString(Object value);

    /**
     * 打印<code>value</code>字面表示到<code>builder</code>
     * 
     * @param builder @see StringBuilder
     * @param value 给定对象
     */
    public abstract void append(StringBuilder builder, Object value);

    public static PrimitiveArrayType find(Class<?> type) {
        for (PrimitiveArrayType arrayType : PrimitiveArrayType.values()) {
            if (arrayType.hit(type)) {
                return arrayType;
            }
        }

        return null;
    }

}
