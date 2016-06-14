/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DynamicTemplateServiceTest {

	@Test
	public void dynamicTemplate() throws IllegalAccessException {
		final Set<TemplateClassDefinition> definitions = Sets.newHashSet();
		definitions.add(new TemplateClassDefinition(TemplateClass.class));
		final TemplateClassRegister register = new TemplateClassRegister(definitions);

		final TemplateResource resource = new TemplateResource(TPL_FILE, CTX_FILE);
		final TemplateLoader tplLoader = new ClasspathTemplateLoader();
		final TemplateContextLoader ctxLoader = new PropertiesTemplateContextLoader();

		final Set<TemplateResource> resources = Sets.newHashSet();
		resources.add(resource);
		final DefaultTemplateService dt = new DefaultTemplateService(tplLoader, ctxLoader, register, resources);
		final TemplateClass tplClass = new TemplateClass(330225198309170333L, "\u9648\u66E6");
		final String content = dt.getDynamicContent(TPL_FILE, tplClass);
		System.out.println(content);
	}

	static class TemplateClass {

		TemplateClass(long number, String name) {
			number_ = number;
			name_ = name;
		}

		@SuppressWarnings("unused")
		@TemplateVariable(variable = "gmsfhm")
		private final long number_;
		@SuppressWarnings("unused")
		@TemplateVariable(variable = "xm")
		private final String name_;
	}

	private static final String TPL_FILE = "test.tpl";
	private static final String CTX_FILE = "template-context-static.properties";
}
