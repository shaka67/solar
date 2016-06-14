/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.image.Image;
import com.ouer.solar.image.ImageApi;
import com.ouer.solar.image.ImageRetrieveDetails;
import com.ouer.solar.image.ImagePrivateRetrieve;
import com.ouer.solar.image.ImagePublicRetrieve;
import com.ouer.solar.image.ImageStoreException;
import com.ouer.solar.image.ImageStoreRequest;
import com.ouer.solar.image.spring.ImageSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ImageSpringConfig.class, ProfileSpringConfig.class })
public class QiniuMageServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ImageApi imageApi;

	@Test
	public void testStore() throws FileNotFoundException, ImageStoreException {
		final ImageStoreRequest request = new ImageStoreRequest("c2c-gamma", new FileInputStream(new File("e:/xiaojingyu.jpg")));
		final Image image = imageApi.store(request);
		System.err.println(image);
	}

	@Test
	public void testRetrieve() {
		final ImageRetrieveDetails details = new ImageRetrieveDetails("FrPNIUYMx7DpC5KNq__DdMORN5u4", 200, 200);
		final ImagePrivateRetrieve request = new ImagePrivateRetrieve("c2c-gamma", details);
		final String url = imageApi.retrieve(request);
		System.err.println(url);
	}

	@Test
	public void testRetrieve2() {
		final ImagePublicRetrieve request = new ImagePublicRetrieve("qiniu|xaya|FrPNIUYMx7DpC5KNq__DdMORN5u4", 200, 200);
		final String url = imageApi.retrieve(request);
		System.err.println(url);
	}
}
