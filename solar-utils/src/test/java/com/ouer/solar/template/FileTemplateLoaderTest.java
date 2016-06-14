/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import org.junit.Test;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FileTemplateLoaderTest {

	@Test
	public void loadTemplateFile() {
		final TemplateLoader loader = new FileTemplateLoader();
		final String content = loader.loadTemplate(TPL_FILE);
		System.out.println(content);
	}

	private static final String TPL_FILE = "src/test/resources/test.tpl";
}
