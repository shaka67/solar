/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 数组转换接口，将对象转换成基本类型的数组
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 上午11:50:11
 */
public interface ArrayConvertable {

    /**
     * 将对象<code>value</code>转换成<code>boolean</code>数组，对应<code>Class</code>字面值为<code>[Z</code>
     * 
     * @param value 需要转换的对象
     * @return <code>boolean</code>数组
     */
    boolean[] convert2Boolean(Object value);

    /**
     * 将对象<code>value</code>转换成<code>byte</code>数组，对应<code>Class</code>字面值为<code>[B</code>
     * 
     * @param value 需要转换的对象
     * @return <code>byte</code>数组
     */
    byte[] convert2Byte(Object value);

    /**
     * 将对象<code>value</code>转换成<code>char</code>数组，对应<code>Class</code>字面值为<code>[C</code>
     * 
     * @param value 需要转换的对象
     * @return <code>char</code>数组
     */
    char[] convert2Char(Object value);

    /**
     * 将对象<code>value</code>转换成<code>double</code>数组，对应<code>Class</code>字面值为<code>[D</code>
     * 
     * @param value 需要转换的对象
     * @return <code>double</code>数组
     */
    double[] convert2Double(Object value);

    /**
     * 将对象<code>value</code>转换成<code>float</code>数组，对应<code>Class</code>字面值为<code>[F</code>
     * 
     * @param value 需要转换的对象
     * @return <code>float</code>数组
     */
    float[] convert2Float(Object value);

    /**
     * 将对象<code>value</code>转换成<code>int</code>数组，对应<code>Class</code>字面值为<code>[I</code>
     * 
     * @param value 需要转换的对象
     * @return <code>int</code>数组
     */
    int[] convert2Int(Object value);

    /**
     * 将对象<code>value</code>转换成<code>long</code>数组，对应<code>Class</code>字面值为<code>[J</code>
     * 
     * @param value 需要转换的对象
     * @return <code>long</code>数组
     */
    long[] convert2Long(Object value);

    /**
     * 将对象<code>value</code>转换成<code>short</code>数组，对应<code>Class</code>字面值为<code>[S</code>
     * 
     * @param value 需要转换的对象
     * @return 转换成<code>short</code>数组
     */
    short[] convert2Short(Object value);

    /**
     * 将对象<code>value</code>转换成<code>String</code>数组，对应<code>Class</code>字面值为<code>[java.lang.String;</code>
     * 
     * @param value 需要转换的对象
     * @return 转换成<code>String</code>数组
     */
    String[] convert2String(Object value);

}
