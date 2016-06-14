/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 * 有关<code>hashCode()</code>的方法
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * <p>
 * 使用场景：<br>
 * 
 * <pre>
 * int result = HashCode.SEED;
 * result = HashCode.hash(result, value1);
 * result = HashCode.hash(result, value2);
 * ...
 * return result;
 * </pre>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午5:11:08
 */
public abstract class HashCode {

    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;

    /** 初始化种子 */
    public static final int SEED = 173;

    /** 初始化质数 */
    private static final int PRIME = 37;

    /**
     * 混乱<code>hashCode</code>
     * 
     * @param hashCode @see {@link #hashCode()}
     * @return 混乱后的值
     */
    public static int smear(int hashCode) {
        return C2 * Integer.rotateLeft(hashCode * C1, 15);
    }

    /**
     * 通过给定的布尔型<code>aBoolean</code>计算哈希值
     * 
     * @param seed 种子
     * @param aBoolean 给定的布尔型
     * @return 哈希值
     */
    public static int hash(int seed, boolean aBoolean) {
        return (PRIME * seed) + (aBoolean ? 1231 : 1237);
    }

    /**
     * 通过给定的布尔型<code>aBoolean</code>计算哈希值
     * 
     * @param aBoolean 给定的布尔型
     * @return 哈希值
     */
    public static int hash(boolean aBoolean) {
        return hash(SEED, aBoolean);
    }

    /**
     * 通过给定的布尔型数组<code>booleanArray</code>计算哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param booleanArray 给定的布尔型数组
     * @return 哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, boolean[] booleanArray) {
        if (booleanArray == null) {
            return 0;
        }
        for (boolean aBoolean : booleanArray) {
            seed = hash(seed, aBoolean);
        }
        return seed;
    }

    /**
     * 通过给定的布尔型数组<code>booleanArray</code>计算哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param booleanArray 给定的布尔型数组
     * @return 哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(boolean[] booleanArray) {
        return hash(SEED, booleanArray);
    }

    /**
     * 通过给定的布尔型数组<code>booleanArray</code>计算哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param booleanArray 给定的布尔型数组
     * @return 哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashBooleanArray(int seed, boolean...booleanArray) {
        return hash(seed, booleanArray);
    }

    /**
     * 通过给定的布尔型数组<code>booleanArray</code>计算哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param booleanArray 给定的布尔型数组
     * @return 哈希值，如果<code>booleanArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashBooleanArray(boolean...booleanArray) {
        return hashBooleanArray(SEED, booleanArray);
    }

    /**
     * 通过给定的字符型<code>aChar</code>计算哈希值
     * 
     * @param seed 种子
     * @param aChar 给定的字符型
     * @return 哈希值
     */
    public static int hash(int seed, char aChar) {
        return (PRIME * seed) + aChar;
    }

    /**
     * 通过给定的字符型<code>aChar</code>计算哈希值
     * 
     * @param aChar 给定的字符型
     * @return 哈希值
     */
    public static int hash(char aChar) {
        return hash(SEED, aChar);
    }

    /**
     * 通过给定的字符型数组<code>charArray</code>计算哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param charArray 给定的字符型数组
     * @return 哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, char[] charArray) {
        if (charArray == null) {
            return 0;
        }
        for (char aChar : charArray) {
            seed = hash(seed, aChar);
        }
        return seed;
    }

    /**
     * 通过给定的字符型数组<code>charArray</code>计算哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param charArray 给定的字符型数组
     * @return 哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(char[] charArray) {
        return hash(SEED, charArray);
    }

    /**
     * 通过给定的字符型数组<code>charArray</code>计算哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param charArray 给定的字符型数组
     * @return 哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashCharArray(int seed, char...charArray) {
        return hash(seed, charArray);
    }

    /**
     * 通过给定的字符型数组<code>charArray</code>计算哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param charArray 给定的字符型数组
     * @return 哈希值，如果<code>charArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashCharArray(char...charArray) {
        return hashCharArray(SEED, charArray);
    }

    /**
     * 通过给定的整型<code>anInt</code>计算哈希值
     * 
     * @param seed 种子
     * @param anInt 给定的整型
     * @return 哈希值
     */
    public static int hash(int seed, int anInt) {
        return (PRIME * seed) + anInt;
    }

    /**
     * 通过给定的整型<code>anInt</code>计算哈希值
     * 
     * @param anInt 给定的整型
     * @return 哈希值
     */
    public static int hash(int anInt) {
        return hash(SEED, anInt);
    }

    /**
     * 通过给定的整型数组<code>intArray</code>计算哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param intArray 给定的整型数组
     * @return 哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, int[] intArray) {
        if (intArray == null) {
            return 0;
        }
        for (int anInt : intArray) {
            seed = hash(seed, anInt);
        }
        return seed;
    }

    /**
     * 通过给定的整型数组<code>intArray</code>计算哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param intArray 给定的整型数组
     * @return 哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int[] intArray) {
        return hash(SEED, intArray);
    }

    /**
     * 通过给定的整型数组<code>intArray</code>计算哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param intArray 给定的整型数组
     * @return 哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashIntArray(int seed, int...intArray) {
        return hash(seed, intArray);
    }

    /**
     * 通过给定的整型数组<code>intArray</code>计算哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param intArray 给定的整型数组
     * @return 哈希值，如果<code>intArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashIntArray(int...intArray) {
        return hashIntArray(SEED, intArray);
    }

    /**
     * 通过给定的short型数组<code>shortArray</code>计算哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param shortArray 给定的short型数组
     * @return 哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, short[] shortArray) {
        if (shortArray == null) {
            return 0;
        }
        for (short aShort : shortArray) {
            seed = hash(seed, aShort);
        }
        return seed;
    }

    /**
     * 通过给定的short型数组<code>shortArray</code>计算哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param shortArray 给定的short型数组
     * @return 哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(short[] shortArray) {
        return hash(SEED, shortArray);
    }

    /**
     * 通过给定的short型数组<code>shortArray</code>计算哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param shortArray 给定的short型数组
     * @return 哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashShortArray(int seed, short...shortArray) {
        return hash(seed, shortArray);
    }

    /**
     * 通过给定的short型数组<code>shortArray</code>计算哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param shortArray 给定的short型数组
     * @return 哈希值，如果<code>shortArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashShortArray(short...shortArray) {
        return hash(SEED, shortArray);
    }

    /**
     * 通过给定的字节型数组<code>byteArray</code>计算哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param byteArray 给定的字节型数组
     * @return 哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, byte[] byteArray) {
        if (byteArray == null) {
            return 0;
        }
        for (byte aByte : byteArray) {
            seed = hash(seed, aByte);
        }
        return seed;
    }

    /**
     * 通过给定的字节型数组<code>byteArray</code>计算哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param byteArray 给定的字节型数组
     * @return 哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(byte[] byteArray) {
        return hash(SEED, byteArray);
    }

    /**
     * 通过给定的字节型数组<code>byteArray</code>计算哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param byteArray 给定的字节型数组
     * @return 哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashByteArray(int seed, byte...byteArray) {
        return hash(seed, byteArray);
    }

    /**
     * 通过给定的字节型数组<code>byteArray</code>计算哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param byteArray 给定的字节型数组
     * @return 哈希值，如果<code>byteArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashByteArray(byte...byteArray) {
        return hashByteArray(SEED, byteArray);
    }

    /**
     * 通过给定的长整型<code>aLong</code>计算哈希值
     * 
     * @param seed 种子
     * @param aLong 给定的长整型
     * @return 哈希值
     */
    public static int hash(int seed, long aLong) {
        return (PRIME * seed) + (int) (aLong ^ (aLong >>> 32));
    }

    /**
     * 通过给定的长整型<code>aLong</code>计算哈希值
     * 
     * @param aLong 给定的长整型
     * @return 哈希值
     */
    public static int hash(long aLong) {
        return hash(SEED, aLong);
    }

    /**
     * 通过给定的长整型数组<code>longArray</code>计算哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param longArray 给定的长整型数组
     * @return 哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, long[] longArray) {
        if (longArray == null) {
            return 0;
        }
        for (long aLong : longArray) {
            seed = hash(seed, aLong);
        }
        return seed;
    }

    /**
     * 通过给定的长整型数组<code>longArray</code>计算哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param longArray 给定的长整型数组
     * @return 哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(long[] longArray) {
        return hash(SEED, longArray);
    }

    /**
     * 通过给定的长整型数组<code>longArray</code>计算哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param longArray 给定的长整型数组
     * @return 哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashLongArray(int seed, long...longArray) {
        return hash(seed, longArray);
    }

    /**
     * 通过给定的长整型数组<code>longArray</code>计算哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param longArray 给定的长整型数组
     * @return 哈希值，如果<code>longArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashLongArray(long...longArray) {
        return hashLongArray(SEED, longArray);
    }

    /**
     * 通过给定的float型<code>aFloat</code>计算哈希值
     * 
     * @param seed 种子
     * @param aFloat 给定的float型
     * @return 哈希值
     */
    public static int hash(int seed, float aFloat) {
        return hash(seed, Float.floatToIntBits(aFloat));
    }

    /**
     * 通过给定的float型<code>aFloat</code>计算哈希值
     * 
     * @param aFloat 给定的float型
     * @return 哈希值
     */
    public static int hash(float aFloat) {
        return hash(SEED, aFloat);
    }

    /**
     * 通过给定的float型数组<code>floatArray</code>计算哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param floatArray 给定的float型数组
     * @return 哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, float[] floatArray) {
        if (floatArray == null) {
            return 0;
        }
        for (float aFloat : floatArray) {
            seed = hash(seed, aFloat);
        }
        return seed;
    }

    /**
     * 通过给定的float型数组<code>floatArray</code>计算哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param floatArray 给定的float型数组
     * @return 哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(float[] floatArray) {
        return hash(SEED, floatArray);
    }

    /**
     * 通过给定的float型数组<code>floatArray</code>计算哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param floatArray 给定的float型数组
     * @return 哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashFloatArray(int seed, float...floatArray) {
        return hash(seed, floatArray);
    }

    /**
     * 通过给定的float型数组<code>floatArray</code>计算哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param floatArray 给定的float型数组
     * @return 哈希值，如果<code>floatArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashFloatArray(float...floatArray) {
        return hashFloatArray(SEED, floatArray);
    }

    /**
     * 通过给定的double型<code>aDouble</code>计算哈希值
     * 
     * @param seed 种子
     * @param aDouble 给定的double型
     * @return 哈希值
     */
    public static int hash(int seed, double aDouble) {
        return hash(seed, Double.doubleToLongBits(aDouble));
    }

    /**
     * 通过给定的double型<code>aDouble</code>计算哈希值
     * 
     * @param aDouble 给定的double型
     * @return 哈希值
     */
    public static int hash(double aDouble) {
        return hash(SEED, aDouble);
    }

    /**
     * 通过给定的double型数组<code>doubleArray</code>计算哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param doubleArray 给定的double型数组
     * @return 哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(int seed, double[] doubleArray) {
        if (doubleArray == null) {
            return 0;
        }
        for (double aDouble : doubleArray) {
            seed = hash(seed, aDouble);
        }
        return seed;
    }

    /**
     * 通过给定的double型数组<code>doubleArray</code>计算哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param doubleArray 给定的double型数组
     * @return 哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hash(double[] doubleArray) {
        return hash(SEED, doubleArray);
    }

    /**
     * 通过给定的double型数组<code>doubleArray</code>计算哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param seed 种子
     * @param doubleArray 给定的double型数组
     * @return 哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashDoubleArray(int seed, double...doubleArray) {
        return hash(seed, doubleArray);
    }

    /**
     * 通过给定的double型数组<code>doubleArray</code>计算哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>。
     * 
     * @param doubleArray 给定的double型数组
     * @return 哈希值，如果<code>doubleArray</code>为<code>null</code>，则返回<code>0</code>
     */
    public static int hashDoubleArray(double...doubleArray) {
        return hashDoubleArray(SEED, doubleArray);
    }

    /**
     * 计算给定对象<code>aObject</code>的哈希值
     * 
     * @param seed 种子
     * @param aObject 定对象<code>aObject</code>
     * @return 哈希值
     */
    public static int hash(int seed, Object aObject) {
        int result = seed;
        if (aObject == null) {
            return hash(result, 0);
        }
        if (aObject.getClass().isArray() == false) {
            return hash(result, aObject.hashCode());
        }

        Object[] objects = (Object[]) aObject;
        int length = objects.length;
        for (int idx = 0; idx < length; ++idx) {
            result = hash(result, objects[idx]);
        }

        return result;
    }

    /**
     * 计算给定对象<code>aObject</code>的哈希值
     * 
     * @param aObject 定对象<code>aObject</code>
     * @return 哈希值
     */
    public static int hash(Object aObject) {
        return hash(SEED, aObject);
    }

    /**
     * 计算给定对象<code>aObject</code>的哈希值
     * 
     * @param seed 种子
     * @param aObjects 定对象<code>aObjects</code>
     * @return 哈希值
     */
    public static int hash(int seed, Object...aObjects) {
        int result = seed;
        if (aObjects == null) {
            return hash(result, 0);
        }

        Object[] objects = aObjects;
        int length = objects.length;
        for (int idx = 0; idx < length; ++idx) {
            result = hash(result, objects[idx]);
        }

        return result;
    }

    /**
     * 计算给定对象<code>aObject</code>的哈希值
     * 
     * @param objects 定对象<code>objects</code>
     * @return 哈希值
     */
    public static int hash(Object...objects) {
        return hash(SEED, objects);
    }

}