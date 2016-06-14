/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class KryoSerializer implements Serializer {

	private final static Kryo kryo = new Kryo();

	@Override
	public byte[] serialize(Object obj) throws IOException {
		Output output = null;
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryo.writeClassAndObject(output, obj);
			output.flush();
			return baos.toByteArray();
		}finally{
			if(output != null) {
				output.close();
			}
		}
	}

	@Override
	public Object deserialize(byte[] bits) throws IOException {
		if(bits == null || bits.length == 0) {
			return null;
		}
		Input ois = null;
		try {
			final ByteArrayInputStream bais = new ByteArrayInputStream(bits);
			ois = new Input(bais);
			return kryo.readClassAndObject(ois);
		} finally {
			if(ois != null) {
				ois.close();
			}
		}
	}

}
