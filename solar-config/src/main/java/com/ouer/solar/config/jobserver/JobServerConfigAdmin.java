/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.jobserver;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface JobServerConfigAdmin {

	public boolean write(JobServerSpecialConfig config);

	public boolean remove(String appId);
}
