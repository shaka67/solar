/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.image.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.image.ImageConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class ImageConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ImageConfigMapper mapper;

	@Test
	public void testInsert() {
		final ImageConfig config = new ImageConfig();
		config.setAppId("c2c-gamma");
		config.setThirdparty("qiniu");
		config.setAccessKey("pvq0yhV90DZRWntM0jZLI_KkSgpwT2rWg6NUDNkT");
		config.setSecretKey("iGExckccKW6zoh67BKq0etiYB2bZ6gPS0_xNAKEX");
		config.setNamespace("xaya");
		config.setStoreConfig("{}");
		mapper.insert(config);
	}
}
