/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

/**
 * Compare Util
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-21 下午7:06:41
 */
public abstract class CompareUtil {

    public static <T extends Comparable<? super T>> boolean between(T key, T begin, T end) {
        if (ObjectUtil.isAnyNull(key, begin, end)) {
            return false;
        }

        if (begin.compareTo(end) > 0) {
            return false;
        }

        return key.compareTo(begin) >= 0 && key.compareTo(begin) <= 0;
    }

}
