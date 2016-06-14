/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.logger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月15日 下午7:14:16
 */
public class CachedLogger implements LoggerProvider {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Logger getLogger() {
        return logger;
    }

}
