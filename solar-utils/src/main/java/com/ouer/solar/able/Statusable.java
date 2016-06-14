/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Statusable 
{
	public int getCode();
	
	public static final int SUCCESS = 1;
	public static final String SUCCESS_DESC = "success";
}
