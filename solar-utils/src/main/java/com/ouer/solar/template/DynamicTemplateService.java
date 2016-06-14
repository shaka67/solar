/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface DynamicTemplateService
{
    public String getDynamicContent(String tplPath, Object... object) throws IllegalAccessException;
}
