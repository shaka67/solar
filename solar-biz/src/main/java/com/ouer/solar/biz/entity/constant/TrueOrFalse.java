/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity.constant;

import com.ouer.solar.able.Valuable;

/**
 * 布尔型的枚举
 * 
 * <table border="1">
 * <tr>
 * <th><code>enum</code></th>
 * <th><code>code</code></th>
 * </tr>
 * <tr>
 * <th><code>{@link #TRUE}</code></th>
 * <th><code>1</code></th> </th>
 * </tr>
 * <tr>
 * <th><code>{@link #FALSE}</code></th>
 * <th><code>0</code></th> </th>
 * </tr>
 * </table>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月6日 下午3:07:32
 */

public enum TrueOrFalse implements Valuable<Integer> {
    /**
     * true
     */
    TRUE(1),
    /**
     * false
     */
    FALSE(0);

    private int value;

    TrueOrFalse(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }
}
