/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.mogilefs.MogileExecutor;
import com.ouer.solar.mogilefs.Trackers;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class PoolableExecutorFactory implements
		PoolableObjectFactory<MogileExecutor> {

	private static final Logger LOG = LoggerFactory.getLogger(PoolableExecutorFactory.class);

	private final Trackers trackers;

	public PoolableExecutorFactory(Trackers trackers) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("new executor factory created");
        }

		this.trackers = trackers;
	}

	@Override
    public MogileExecutor makeObject() throws Exception {
		try {
			final MogileExecutor executor = new MogileExecutor(trackers);

			if (LOG.isDebugEnabled()) {
				LOG.debug("making object [{}]", executor);
            }

			return executor;
		} catch (final Exception e) {
			LOG.error("problem making executor", e);

			throw e;
		}
	}

	@Override
    public void destroyObject(MogileExecutor executor) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("destroying object [{}]", executor);
        }

		executor.destroy();
	}

	@Override
    public boolean validateObject(MogileExecutor executor) {

		final boolean connected = executor.isConnected();

		if (!connected) {
			LOG.warn("validating  [{}]. Not valid! Last err was: [{}]",
					executor, executor.getLastErr());
			return connected;

		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("validating [{}]. validated", executor);
		}

		return connected;
	}

	@Override
    public void activateObject(MogileExecutor executor) throws Exception {
		// nothing to do
		if (LOG.isDebugEnabled()) {
			LOG.debug("activating object [{}]", executor);
		}

	}

	@Override
    public void passivateObject(MogileExecutor executor) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("passivating object [{}]", executor);
		}
	}

}
