/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-1-12 下午10:54:25
 */
public class ThreadPool extends CachedLogger {

    private static ThreadPool instance = new ThreadPool();

    private final ExecutorService pool;

    private ThreadPool() {
        pool = Executors.newCachedThreadPool(new SimpleThreadFactory("ThreadPool"));
    }

    public static ThreadPool getInstance() {
        return instance;
    }

    public void execute(String name, Runnable runnable) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Executing thread: " + name);
    	}
        try {
            pool.execute(runnable);
        } catch (RuntimeException e) {
            logger.error("Trapped exception on thread: " + name, e);
        }
    }

}
