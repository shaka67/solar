/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mogilefs;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface MogilefsConfigAdmin {

	public boolean write(MogilefsConfig config);

	public boolean remove(String appId);
}
