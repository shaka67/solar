///**
// * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
// */
//package com.ouer.solar.template;
//
///**
// *
// * @author <a href="indra@ixiaopu.com">chenxi</a>
// */
//
//public class DynamicTemplateServiceTestCase {
//
//	/**
//	 * @param args
//	 * @throws IllegalAccessException
//	 */
//	public static void main(String[] args) throws IllegalAccessException {
//		Injector injector = Guice.createInjector(new DynamicTemplateTestModule());
//		DefaultTemplateService service = injector.getInstance(DefaultTemplateService.class);
//		String content = service.getDynamicContent(TPL_FILE, new TemplateClass(330225198309170333L, "\u9648\u66E6"));
//		System.out.println(content);
//	}
//
//	static class DynamicTemplateTestModule extends AbstractModule {
//
//		@Override
//		protected void configure() {
//			install(new AppDomainModule());
//			install(new TemplateModule() {
//
//				@Override
//				protected void configureTemplates()
//			    {
//					serveTplClass(TemplateClass.class);
//					serveTplResource(new TemplateResource(TPL_FILE, CTX_FILE));
//			    }
//			});
//		}
//	}
//
//	static class TemplateClass {
//
//		TemplateClass(long number, String name) {
//			number_ = number;
//			name_ = name;
//		}
//
//		@SuppressWarnings("unused")
//		@TemplateVariable(variable = "gmsfhm")
//		private final long number_;
//		@SuppressWarnings("unused")
//		@TemplateVariable(variable = "xm")
//		private final String name_;
//	}
//
//	private static final String TPL_FILE = "test.tpl";
//	private static final String CTX_FILE = "template-context-static.properties";
//}
