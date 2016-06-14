/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs.dynamic;

import com.ouer.solar.annotation.JMX;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@JMX
public class MogilefsRuntimeChanger {

	private final MogilefsManager manager;

	public MogilefsRuntimeChanger(MogilefsManager manager) {
		this.manager = manager;
	}

	public void reload(final String appId) throws Exception {
		synchronized (manager) {
			manager.removeMogileFS(appId);
			manager.getMogileFS(appId);
		}
	}

}
