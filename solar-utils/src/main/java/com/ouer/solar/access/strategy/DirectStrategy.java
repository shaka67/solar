/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.strategy;

import com.ouer.solar.access.AccessStrategy;

/**
 * 直接通过<code>id</code>对应
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午4:41:28
 */
public class DirectStrategy implements AccessStrategy {

    @Override
    public String find(long id) {
        return String.valueOf(id);
    }

}
