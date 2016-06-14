/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ouer.solar.cache.CacheException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JavaSerializer implements Serializer {

	@Override
	public byte[] serialize(Object obj) throws IOException {
		ObjectOutputStream oos = null;
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (final IOException e) {}
			}
		}
	}

	@Override
	public Object deserialize(byte[] bits) throws IOException {
		if(bits == null || bits.length == 0) {
			return null;
		}
		ObjectInputStream ois = null;
		try {
			final ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (final ClassNotFoundException e) {
			throw new CacheException(e);
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (final IOException e) {}
			}
		}
	}

}
