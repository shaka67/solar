/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

/**
 * An abstract image class which encapsulates necessary information like:
 *  unique key to visit the image,
 *  image size,
 *  image width,
 *  image height,
 *  image url and so on
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Image {

	private ImageKey imageKey;
	private long size;
	private int width;
	private int height;
	private String url;

	public ImageKey getImageKey() {
		return imageKey;
	}

	public void setImageKey(ImageKey imageKey) {
		this.imageKey = imageKey;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Image [imageKey=" + imageKey + ", size=" + size + ", width=" + width
				+ ", height=" + height + ", url=" + url + "]";
	}

}
