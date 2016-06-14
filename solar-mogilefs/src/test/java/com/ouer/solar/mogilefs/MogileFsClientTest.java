/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.mogilefs;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ouer.solar.mogilefs.dynamic.MogilefsManager;
import com.ouer.solar.mogilefs.exception.MogileException;
import com.ouer.solar.mogilefs.spring.MogilefsSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@ActiveProfiles("prod")
@ContextConfiguration(classes = { MogilefsSpringConfig.class, ProfileSpringConfig.class })
public class MogileFsClientTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MogilefsManager manager;

	@Test
	public void testMail() throws MogileException, IOException {
		final Mogilefs mogilefs = manager.getMogileFS("qianfan");
		final InputStream resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mailfroms.xml");
		mogilefs.storeFile("mail-qianfan.xml", resource);

		System.err.println(new String(mogilefs.getFileBytes("mail-qianfan.xml")));
	}

	@Test
	public void testImage() throws MogileException, IOException {
		final Mogilefs mogilefs = manager.getMogileFS("indra");
		final InputStream resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("images.xml");
		mogilefs.storeFile("image-qiniu-1000.xml", resource);

		System.err.println(new String(mogilefs.getFileBytes("image-qiniu-1000.xml")));
	}

	@Test
	public void testSearch() throws MogileException, IOException {
		final Mogilefs mogilefs = manager.getMogileFS("c2c-config");
		InputStream resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("definition-index.xml");
		mogilefs.storeFile("es-index.xml", resource);
		System.err.println(new String(mogilefs.getFileBytes("es-index.xml")));

		resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("definition-result.xml");
		mogilefs.storeFile("es-result.xml", resource);
		System.err.println(new String(mogilefs.getFileBytes("es-result.xml")));

		resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("definition-search.xml");
		mogilefs.storeFile("es-search.xml", resource);
		System.err.println(new String(mogilefs.getFileBytes("es-search.xml")));

		resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("IndexMapper.xml");
		mogilefs.storeFile("IndexMapper-indra.xml", resource);
		System.err.println(new String(mogilefs.getFileBytes("IndexMapper-indra.xml")));

		resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("MapperConfig.xml");
		mogilefs.storeFile("MapperConfig-indra.xml", resource);
		System.err.println(new String(mogilefs.getFileBytes("MapperConfig-indra.xml")));
	}

	@Test
	public void clearSearch() throws MogileException, IOException {
		final Mogilefs mogilefs = manager.getMogileFS("c2c");
		mogilefs.delete("es-index.xml");
		mogilefs.delete("es-result.xml");
		mogilefs.delete("es-search.xml");
		mogilefs.delete("IndexMapper-indra.xml");
		mogilefs.delete("MapperConfig-indra.xml");
	}
}
