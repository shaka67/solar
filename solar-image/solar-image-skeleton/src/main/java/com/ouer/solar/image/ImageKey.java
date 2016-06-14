/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import com.ouer.solar.StringPool;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageKey {

	private final String thirdparty;
	private final String namespace;
	private final String key;

	public ImageKey(String thirdparty, String namespace, String key) {
		this.thirdparty = thirdparty;
		this.namespace = namespace;
		this.key = key;
	}

	public String getThirdparty() {
		return thirdparty;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return thirdparty + StringPool.Symbol.PIPE + namespace + StringPool.Symbol.PIPE + key;
	}

}
