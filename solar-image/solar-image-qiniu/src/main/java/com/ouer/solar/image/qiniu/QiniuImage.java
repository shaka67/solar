/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

import com.ouer.solar.image.Image;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QiniuImage extends Image {

	private String hash;
	private String key;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "QiniuImage [hash=" + hash + ", key=" + key + ", getImageKey()="
				+ getImageKey() + ", getSize()=" + getSize() + ", getWidth()="
				+ getWidth() + ", getHeight()=" + getHeight() + ", getUrl()="
				+ getUrl() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}


}
