/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.util.Map;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface TemplateContextLoader
{
	public Map<String, Object> getContext(String path);
}
