/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import com.ouer.solar.config.image.ImageConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageStoreDetails {

	private final ImageConfig config;
	private final Image image;

	public ImageStoreDetails(ImageConfig config, Image image) {
		this.config = config;
		this.image = image;
	}

	public ImageConfig getConfig() {
		return config;
	}

	public Image getImage() {
		return image;
	}

}
