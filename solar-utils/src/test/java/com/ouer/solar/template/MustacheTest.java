/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.util.Map;

import org.junit.Test;

import com.samskivert.mustache.Mustache;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MustacheTest {

	@Test
	public void computeStaticTemplate() {
		final TemplateLoader tplLoader = new ClasspathTemplateLoader();
		final String content = tplLoader.loadTemplate(TPL_FILE);
		final TemplateContextLoader ctxLoader = new PropertiesTemplateContextLoader();
		final Map<String, Object> ctx = ctxLoader.getContext(CTX_FILE);
		System.out.println(Mustache.compiler().compile(content).execute(ctx));
	}

	private static final String TPL_FILE = "test.tpl";
	private static final String CTX_FILE = "template-context.properties";
}
