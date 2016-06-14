/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache;

import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.junit.Test;

import com.ouer.solar.bean.sample.BeanSampleA;
import com.ouer.solar.cache.BeanInfoCache;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年10月16日 下午7:17:46
 */
public class BeanInfoCacheTest extends CachedLogger {

    @Test
    public void getPropertyDescriptor() {
        Map<String, PropertyDescriptor> propertyMap =
                BeanInfoCache.getInstance().getPropertyDescriptor(BeanSampleA.class);
        for (Map.Entry<String, PropertyDescriptor> entry : propertyMap.entrySet()) {
            logger.info(entry.getKey());
            logger.info(entry.getValue().getName());
        }
    }

    @Test
    public void getMethodDescriptor() {
        Map<String, MethodDescriptor> propertyMap = BeanInfoCache.getInstance().getMethodDescriptor(BeanSampleA.class);
        for (Map.Entry<String, MethodDescriptor> entry : propertyMap.entrySet()) {
            logger.info(entry.getKey());
            logger.info(entry.getValue().getName());
        }
    }

}
