/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import java.io.InputStream;

import com.ouer.solar.able.AppConfigurable;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageStoreRequest implements AppConfigurable {

	private final String appId;
	private final InputStream input;
	private final String key;

	public ImageStoreRequest(String appId, InputStream input) {
		this(appId, input, null);
	}

	public ImageStoreRequest(String appId, InputStream input, String key) {
		this.appId = appId;
		this.input = input;
		this.key = key;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public InputStream getInput() {
		return input;
	}

	public String getKey() {
		return key;
	}

}
