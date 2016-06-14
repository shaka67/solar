/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.io.IOException;

import mx4j.tools.adaptor.http.HttpAdaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MBeanHttpAdaptor {

	private static Logger LOG = LoggerFactory.getLogger(MBeanHttpAdaptor.class);

	private final HttpAdaptor httpAdaptor;
	private final String username;
	private final String password;

	public MBeanHttpAdaptor(HttpAdaptor httpAdaptor, String username,
			String password) {
		this.httpAdaptor = httpAdaptor;
		this.username = username;
		this.password = password;
	}

	public void start() {
		httpAdaptor.addAuthorization(username, password);
		try {
			httpAdaptor.start();
		} catch (final IOException e) {
			LOG.error("", e);
		}
	}

	public void stop() {
		httpAdaptor.stop();
	}

}
