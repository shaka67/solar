/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.shared;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RedisCacheUtils {

	@SuppressWarnings("rawtypes")
	public static String encodeKey(String namespace, Object key) {
		if (key instanceof Number) {
			return namespace + ":I:" + key;
		} else {
			final Class keyClass = key.getClass();
			if(String.class.equals(keyClass) || StringBuffer.class.equals(keyClass) || StringBuilder.class.equals(keyClass)) {
				return namespace + ":S:" + key;
			}
		}
		return namespace + ":O:" + key;
	}

	public static String decodeKey(String namespace, String key) {
		return key.substring(namespace.length() + 3);
	}
}
