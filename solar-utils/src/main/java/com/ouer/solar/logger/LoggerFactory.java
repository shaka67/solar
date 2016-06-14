/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.logger;

import java.util.concurrent.ConcurrentMap;

import com.ouer.solar.CollectionUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * 
 * @version create on 2013年8月13日 下午9:30:37
 */
public abstract class LoggerFactory {

    private static final ConcurrentMap<String, Logger> loggerMap = CollectionUtil.createConcurrentMap();

    public static Logger getLogger(String name) {
        Logger logger = loggerMap.get(name);
        if (logger != null) {
            return logger;
        }
        logger = new DefaultLogger(org.slf4j.LoggerFactory.getLogger(name));
        Logger exist = loggerMap.putIfAbsent(name, logger);
        if (exist != null) {
            return exist;
        }
        return logger;
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
