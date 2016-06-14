/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import java.util.Map;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageRetrieveDetails {

	private final String key;
	private final String namespace;
	private final int width;
	private final int height;
	private final BasicImageMode mode;

	private final Map<String, Object> extra;

	public ImageRetrieveDetails(String key) {
		this(key, 0, 0);
	}

	public ImageRetrieveDetails(String key, int width, int height) {
		this(key, null, width, height);
	}

	public ImageRetrieveDetails(String key, String namespace, int width, int height) {
		this(key, namespace, width, height, BasicImageMode.MIN_WH_CUT, null);
	}

	public ImageRetrieveDetails(String key, int width, int height, Map<String, Object> extra) {
		this(key, null, width, height, BasicImageMode.MIN_WH_CUT, extra);
	}

	public ImageRetrieveDetails(String key, String namespace, int width, int height, BasicImageMode mode, Map<String, Object> extra) {
		this.key = key;
		this.namespace = namespace;
		this.width = width;
		this.height = height;
		this.mode = mode;
		this.extra = extra;
	}

	public String getKey() {
		return key;
	}

	public String getNamespace() {
		return namespace;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BasicImageMode getMode() {
		return mode;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public Object getExtraValue(String key) {
		return extra.get(key);
	}

}
