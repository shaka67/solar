/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.entity;

import com.ouer.solar.able.Valuable;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月10日 上午4:58:10
 */

public enum Order implements Valuable<Integer> {
    ASC(0), DESC(-1);

    private int value;

    Order(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }

}
