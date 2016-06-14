/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log the running time of the invocation of the {@link LoggingCallback}.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Clocker {
	
    private static final Logger LOG = LoggerFactory.getLogger(Clocker.class);
    
    private final String subject;
    private final String object;
    
    public Clocker(String subject, String object)
    {
        this.subject = subject;
        this.object = object;
    }

    public <T> T go(Callable<T> callback) throws Exception
    {
        final long start = System.currentTimeMillis();
        try {
            return callback.call();
        } finally {
            final long end = System.currentTimeMillis();
            if (LOG.isInfoEnabled()) {
                LOG.info(subject + " spent " + (end - start) + " milliseconds to finish invocation for " + object);
            }
        }
    }

}