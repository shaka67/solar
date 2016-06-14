/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import org.junit.Test;

import com.ouer.solar.NetworkUtil;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月18日 下午4:00:13
 */
public class NetworkUtilTest extends CachedLogger {

    @Test
    public void getLocalHostname() {
        // rdqawin32-023 10.94.22.70
        // assertEquals("rdqawin32-023", NetworkUtil.getLocalHostname());
        logger.info(NetworkUtil.getLocalHostname());
        logger.info(NetworkUtil.getLocalHostname());
    }

    @Test
    public void getLocalHostIp() {
        logger.info(NetworkUtil.getLocalHostIp());
        // assertEquals("10.94.22.70", NetworkUtil.getLocalHostIp());
    }

}
