/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.security;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DruidPasswordSecurity implements PasswordSecurity {

	@Override
	public String decrypt(String password) throws Exception {
		return ConfigTools.decrypt(password);
	}

	@Override
	public String encrypt(String password) throws Exception {
		return ConfigTools.encrypt(password);
	}

	public static void main(String[] args) throws Exception {
		final String password = args[0];
		System.out.println(ConfigTools.encrypt(password));
	}

}
