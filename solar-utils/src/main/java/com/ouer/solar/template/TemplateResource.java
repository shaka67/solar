/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TemplateResource {

	private final String tplPath;
	private final String ctxPath;

	public TemplateResource(String tplPath, String ctxPath) {
		this.tplPath = tplPath;
		this.ctxPath = ctxPath;
	}

	public String getTplPath() {
		return tplPath;
	}

	public String getCtxPath() {
		return ctxPath;
	}

}
