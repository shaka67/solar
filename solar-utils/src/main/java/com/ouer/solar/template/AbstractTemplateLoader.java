/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractTemplateLoader implements TemplateLoader {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public String loadTemplate(String path) {
		BufferedReader reader = null;
		try {
			reader = getReader(path);
			return readContent(reader);
		} catch (final IOException e) {
			LOG.error("read content from " + path + " failed", e);
            return null;
		} finally {
            if (reader != null) {
                try {
                	reader.close();
                } catch (final IOException e) {
                    LOG.error("read content from " + path + " failed", e);
                    return null;
                }
            }
        }

	}

	protected String readContent(BufferedReader reader) throws IOException {
		String content = "";
        String string = null;
        while ((string = reader.readLine()) != null)
        {
            content += string;
        }
        return content;
	}

	abstract protected BufferedReader getReader(String path) throws IOException;

}
