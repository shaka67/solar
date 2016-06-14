/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImagePrivateRetrieve implements AppConfigurable {

	private final String appId;
	private final ImageRetrieveDetails details;

	public ImagePrivateRetrieve(String appId, ImageRetrieveDetails details) {
		this.appId = appId;
		this.details = details;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public ImageRetrieveDetails getDetails() {
		return details;
	}

}
