/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 无重复唯一认证
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:29:19
 */
public interface Identifiable<UID> {

    /**
     * 设置唯一认证ID
     * 
     * @param uid 唯一认证ID
     */
    void setIdentification(UID uid);

    /**
     * 获取唯一认证ID
     * 
     * @return 唯一认证ID
     */
    UID getIdentification();
}
