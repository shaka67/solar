/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.List;

/**
 * 数字相关的工具类
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-11-23 上午8:57:23
 */
public abstract class NumberUtil {

    /**
     * <p>
     * 将字符串split成整型数字，并以<code>List</code>类型返回，如果字符串为<code>null</code>则返回<code>null</code>。 <br>
     * 该方法不验证数字的正确性，如果分割后得到非数字类型，则会抛出<code>NumberFormatException</code>
     * </p>
     * 
     * @param nums 需要分割的字符串
     * @param separatorChars 分隔符
     * @return 分割成数字的<code>List</code>
     */
    public static List<Integer> split2IntList(String nums, String separatorChars) {
        String[] idArray = StringUtil.split(nums, separatorChars);
        if (ArrayUtil.isEmpty(idArray)) {
            return null;
        }

        List<Integer> result = CollectionUtil.createArrayList(idArray.length);
        for (String id : idArray) {
            result.add(Integer.valueOf(id));
        }

        return result;
    }

    // =========================================================================================================
    // toPrimitive。
    // =========================================================================================================

    public static int toInt(Number wrapped) {
        if (wrapped == null) {
            return -1;
        }

        return wrapped.intValue();
    }

}
