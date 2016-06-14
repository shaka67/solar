/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

import com.ouer.solar.mogilefs.exception.BadHostFormatException;
import com.ouer.solar.mogilefs.exception.NoTrackersException;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SimpleMogilefs extends BaseMogilefs {

	public SimpleMogilefs(Trackers trackers, String domain, String httpPrefix)
			throws BadHostFormatException, NoTrackersException {
		super(trackers, domain, httpPrefix);
	}

	@Override
	protected ObjectPool<MogileExecutor> buildExecutorPool() {

		return new ObjectPool<MogileExecutor>() {

			private MogileExecutor executor = null;

			@Override
            public void addObject() {
			}

			@Override
            public MogileExecutor borrowObject() throws Exception {
				if (executor == null) {
                    executor = new MogileExecutor(trackers);
                }

				return executor;
			}

			@Override
            public void clear() {
			}

			@Override
            public void close() {
			}

			@Override
            public int getNumActive() {
				return 1;
			}

			@Override
            public int getNumIdle() {
				return 0;
			}

			@Override
            public void invalidateObject(MogileExecutor obj) {
				executor = null;
			}

			@Override
            public void returnObject(MogileExecutor obj) {
			}

			@Override
            public void setFactory(PoolableObjectFactory<MogileExecutor> factory) {
			}
		};
	}

}
