/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClasspathTemplateLoader extends AbstractTemplateLoader {

	@Override
	protected BufferedReader getReader(String path) {
		if (path == null) {
            LOG.warn("invalid path");
            return null;
        }

		final InputStream resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path);
		if (resource == null) {
			LOG.warn("invalid resource in " + path);
            return null;
		}

		return new BufferedReader(new InputStreamReader(resource));
	}
}
