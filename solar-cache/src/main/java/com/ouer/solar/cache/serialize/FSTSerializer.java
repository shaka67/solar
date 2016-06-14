/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.ouer.solar.cache.CacheException;

import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FSTSerializer implements Serializer {

	@Override
	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = null;
		FSTObjectOutput fout = null;
		try {
			out = new ByteArrayOutputStream();
			fout = new FSTObjectOutput(out);
			fout.writeObject(obj);
			return out.toByteArray();
		} finally {
			if(fout != null) {
				try {
					fout.close();
				} catch (final IOException e) {}
			}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return in.readObject();
		} catch (final ClassNotFoundException e) {
			throw new CacheException(e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (final IOException e) {}
			}
		}
	}

}
