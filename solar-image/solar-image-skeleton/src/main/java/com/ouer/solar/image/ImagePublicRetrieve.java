/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import java.util.Map;

import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImagePublicRetrieve {

	private final String key;
	private final int width;
	private final int height;
	private final BasicImageMode mode;

	private final Map<String, Object> extra;

	public ImagePublicRetrieve(String key) {
		this(key, 0, 0);
	}

	public ImagePublicRetrieve(String key, int width, int height) {
		this(key, width, height, BasicImageMode.MIN_WH_CUT, null);
	}

	public ImagePublicRetrieve(String key, int width, int height, Map<String, Object> extra) {
		this(key, width, height, BasicImageMode.MIN_WH_CUT, extra);
	}

	public ImagePublicRetrieve(String key, int width, int height, BasicImageMode mode, Map<String, Object> extra) {
		this.key = key;
		this.width = width;
		this.height = height;
		this.mode = mode;
		this.extra = extra;
	}

	public ImageKey getKey() {
		final String[] splits = StringUtil.split(key, StringPool.Symbol.PIPE);
		return new ImageKey(splits[0], splits[1], splits[2]);
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

}
