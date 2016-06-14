/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.concurrent.Callable;

import com.ouer.solar.able.Adaptable;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 上午12:43:07
 */
public class CallableAdapter<T> extends CachedLogger implements Adaptable<Callable<T>, Runnable>, Runnable {

    final Callable<T> task;

    public CallableAdapter(Callable<T> task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            task.call();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public Runnable forNew(final Callable<T> old) {
        return new Runnable() {

            @Override
            public void run() {
                try {
                    old.call();
                } catch (Exception e) {
                    logger.error("", e);
                }

            }
        };
    }

}
