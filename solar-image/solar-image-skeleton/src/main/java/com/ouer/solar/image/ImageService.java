/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import java.io.InputStream;

import com.ouer.solar.Initializable;
import com.ouer.solar.config.image.ImageConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ImageService extends Initializable {

	public Image store(ImageConfig config, InputStream input, String key) throws ImageStoreException;

	public String retrieve(ImageConfig config, ImageRetrieveDetails details);

	public String retrieve(ImagePublicRetrieve request);
}
