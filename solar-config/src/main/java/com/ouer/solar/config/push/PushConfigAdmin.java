/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface PushConfigAdmin {

	public boolean write(PushConfig config);

	public boolean remove(String appId);
}
