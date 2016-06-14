/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FileTemplateLoader extends AbstractTemplateLoader {

	@Override
	protected BufferedReader getReader(String path) throws IOException {
		if (path == null) {
            LOG.warn("invalid path");
            return null;
        }
        final File file = new File(path);
        if (!file.exists()) {
            LOG.warn("file " + file.getPath() + " does not exist");
            return null;
        }

        return new BufferedReader(new FileReader(path));
	}
}
