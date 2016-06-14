/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.security;

/**
 * Password security for not exposing the plain pwd value configured in mailfroms.xml
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface PasswordSecurity
{
	public String decrypt(String password) throws Exception;

	public String encrypt(String password) throws Exception;
}
