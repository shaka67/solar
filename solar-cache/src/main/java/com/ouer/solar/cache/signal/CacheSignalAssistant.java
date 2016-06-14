/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.cache.serialize.Serializer;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class CacheSignalAssistant {

	private static final Logger LOG = LoggerFactory.getLogger(CacheSignalAssistant.class);

	private final Serializer serializer;
	private final int randomSrc;

	public CacheSignalAssistant(Serializer serializer) {
		this.serializer = serializer;
		randomSrc = genRandomSrc();
	}

	public CacheSignal create(CacheSignalOperator operator, String namespace, Object key) {
		return new CacheSignal(randomSrc, operator, namespace, key);
	}

	public byte[] toBytes(CacheSignal signal) {
		byte[] keyBytes = null;
		try {
			keyBytes = serializer.serialize(signal.getKey());
		} catch (final IOException e) {
			LOG.error("", e);
			return null;
		}
		final int namespaceLength = signal.getNamespace().getBytes().length;
		final int keyLength = keyBytes.length;

		final byte[] bytes = new byte[9 + namespaceLength + keyLength];
		System.arraycopy(src2bytes(signal.getSrc()), 0, bytes, 0, 4);
		int index = 4;
		bytes[index] = signal.getOperator().code();
		bytes[++index] = (byte) (namespaceLength >> 8);
		bytes[++index] = (byte) (namespaceLength & 0xFF);
		System.arraycopy(signal.getNamespace().getBytes(), 0, bytes, ++index, namespaceLength);
		index += namespaceLength;
		bytes[index++] = (byte) (keyLength >> 8);
		bytes[index++] = (byte) (keyLength & 0xFF);
		System.arraycopy(keyBytes, 0, bytes, index, keyLength);
		return bytes;
	}

	public CacheSignal parse(byte[] bytes) {
		CacheSignal signal = null;
		try {
			int index = 4;
			final byte opt = bytes[index];
			int namespaceLength = bytes[++index] << 8;
			namespaceLength += bytes[++index];
			if (namespaceLength > 0) {
				final String namespace = new String(bytes, ++index, namespaceLength);
				index += namespaceLength;
				int keyLength = bytes[index++] << 8;
				keyLength += bytes[index++];
				if (keyLength > 0) {
					final byte[] keyBytes = new byte[keyLength];
					System.arraycopy(bytes, index, keyBytes, 0, keyLength);
					final Object key = serializer.deserialize(keyBytes);
					final CacheSignalOperator operator = CacheSignalOperator.fromCode(opt);
					signal = new CacheSignal(bytes2src(bytes), operator, namespace, key);
				}
			}
		} catch(final Exception e) {
			LOG.error("unabled to parse received command.", e);
		}
		return signal;
	}

	public boolean isLocalSignal(CacheSignal signal) {
		return signal.getSrc() == randomSrc;
	}

	private int genRandomSrc() {
		final long time = System.currentTimeMillis();
		final Random random = new Random(time);
		return (int) (random.nextInt(10000) * 1000 + time % 1000);
	}

	private int bytes2src(byte[] bytes) {
		int src = bytes[0] & 0xFF;
		src |= ((bytes[1] << 8) & 0xFF00);
		src |= ((bytes[2] << 16) & 0xFF0000);
		src |= ((bytes[3] << 24) & 0xFF000000);
		return src;
	}

	private byte[] src2bytes(int src) {
        final byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & src);
        bytes[1] = (byte) ((0xff00 & src) >> 8);
        bytes[2] = (byte) ((0xff0000 & src) >> 16);
        bytes[3] = (byte) ((0xff000000 & src) >> 24);
        return bytes;
	}

}
