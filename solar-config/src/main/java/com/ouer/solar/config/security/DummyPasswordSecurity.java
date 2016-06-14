/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.security;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DummyPasswordSecurity implements PasswordSecurity {

	@Override
	public String decrypt(String password) throws Exception {
		return password;
	}

	@Override
	public String encrypt(String password) throws Exception {
		return password;
	}

}
