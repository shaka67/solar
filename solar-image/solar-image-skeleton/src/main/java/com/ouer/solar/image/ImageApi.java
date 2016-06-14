/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface ImageApi
{
	public Image store(ImageStoreRequest request) throws ImageStoreException;

	public String retrieve(ImagePrivateRetrieve request);

	public String retrieve(ImagePublicRetrieve request);
}
