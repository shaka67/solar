/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.security;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class Base64PasswordSecurity implements PasswordSecurity {

	@Override
	public String decrypt(String password) throws Exception { 
        return new String(Base64.decodeBase64(password));
	}

	public String encrypt(String password) {
		return new String(Base64.encodeBase64String(password.getBytes()));
	}
	
	public static void main(String[] args) throws Exception {
		String password = args[0];
		String encode = Base64.encodeBase64String(password.getBytes());
		System.out.println(encode);
		password = new String(Base64.decodeBase64(encode));
		System.out.println(password);
		
		final Base64PasswordSecurity b64PS = new Base64PasswordSecurity();
		encode = b64PS.encrypt(password);
		System.out.println(encode);
		password = b64PS.decrypt(encode);
		System.out.println(password);
	}
}
