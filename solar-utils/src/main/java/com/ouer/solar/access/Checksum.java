/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access;

/**
 * 提供检验码
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午4:53:43
 */
public interface Checksum {

    /**
     * 获取校验码
     * 
     * @return 校验码
     */
    long checksum();

}
