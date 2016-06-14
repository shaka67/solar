/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.concurrent.Callable;

import com.ouer.solar.able.Adaptable;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 上午12:26:20
 */
public class RunnableAdapter<T> implements Adaptable<Runnable, Callable<T>>, Callable<T> {

    final Runnable task;
    final T result;

    public RunnableAdapter(Runnable task, T result) {
        this.task = task;
        this.result = result;
    }

    @Override
	public T call() {
        task.run();
        return result;
    }

    @Override
    public Callable<T> forNew(final Runnable old) {
        return new RunnableAdapter<T>(old, result);
    }

}
