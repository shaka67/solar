/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.strategy;

import com.ouer.solar.access.AccessStrategy;

/**
 * 通过时间戳
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午4:47:10
 */
public class TimestampStrategy implements AccessStrategy {

    @Override
    public String find(long id) {
        return id + "_" + System.currentTimeMillis();
    }

}
