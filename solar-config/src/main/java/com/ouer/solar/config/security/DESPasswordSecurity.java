/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.security;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DESPasswordSecurity implements PasswordSecurity {

	private final Cipher cipher;
	private final SecretKey key;

	@SuppressWarnings("restriction")
	public DESPasswordSecurity() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		key = keyGenerator.generateKey();
		cipher = Cipher.getInstance("DES");
	}

	@Override
	public String encrypt(String str) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		final byte[] src = str.getBytes();
		final byte[] bytes = cipher.doFinal(src);
		return new String(bytes);
	}

	@Override
	public String decrypt(String password) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key);
        final byte[] bytes = cipher.doFinal(password.getBytes());
        return new String(bytes);
	}

}
