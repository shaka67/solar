/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.PropertyUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-4-6 下午4:27:56
 */
public class PropertyUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtilTest.class);

    @Test
    public void loadAllProperties() {
        try {
            Properties properties = PropertyUtil.loadAllProperties("log4j.properties");
            logger.info(properties.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
