/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.strategy;

import com.ouer.solar.access.AccessStrategy;
import com.ouer.solar.access.Checksum;

/**
 * 通过<code>checksum</code>来关联
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午4:40:57
 */
public class ChecksumStrategy implements AccessStrategy {

    private Checksum acs;

    public ChecksumStrategy(Checksum acs) {
        this.acs = acs;
    }

    @Override
    public String find(long id) {
        return id + "_" + acs.checksum();
    }

}
