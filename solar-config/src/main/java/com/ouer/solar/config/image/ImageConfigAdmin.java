/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ImageConfigAdmin {

	public boolean write(ImageConfig config);

	public boolean remove(String appId);
}
