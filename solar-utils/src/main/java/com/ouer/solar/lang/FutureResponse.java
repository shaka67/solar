/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月6日 上午4:05:53
 * @param <V>
 */
public class FutureResponse<V> extends FutureTask<V> {

    public FutureResponse() {
        super(new Callable<V>() {
            @Override
			public V call() throws Exception {
                return null;
            }
        });
    }

    // public void bind(Object key) {
    //
    // }

    @Override
	public void set(V v) {
        super.set(v);
    }
}
