/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.push.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.config.push.PushConfig;
import com.ouer.solar.config.spring.ConfigDalStoreSpring;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("dev")
@ContextConfiguration(classes = { ConfigDalStoreSpring.class, ProfileSpringConfig.class })
public class PushConfigDalTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private PushConfigMapper mapper;

	@Test
	public void testInsert() {
		final PushConfig config = new PushConfig();
		config.setAppId("c2c-gamma");
		config.setThirdparty("xiaomi");
		config.setPackageName("net.riguan.davebella");
		config.setIosSecret("OLnYDWw6rS8p5mX2ddLYTA==");
		config.setAndroidSecret("VWMH/7A8OPZMT2btGemGRA==");
		config.setIosEnv("OFFICIAL");
		config.setAndroidEnv("OFFICIAL");
		config.setNotifyType(1);
		config.setPassThrough(0);
		config.setRetries(0);
		mapper.insert(config);
	}
}
