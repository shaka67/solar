package com.ouer.solar.jmx;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ouer.solar.jmx.MBeanHttpAdaptor;
import com.ouer.solar.jmx.spring.JmxSpringConfig;
import com.ouer.solar.spring.ProfileSpringConfig;

/**
 *
 * @author: chenxi
 */

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JmxSpringConfig.class, ProfileSpringConfig.class })
public class SpringJmxTest {

	@Autowired
	private MBeanExporter exporter;
	@Autowired
	private MBeanHttpAdaptor httpAdaptor;

	@Test
	public void testHttpAdaptor() throws IOException, InterruptedException {
		System.out.println("started");
		Thread.currentThread().sleep(60000);
	}

}
