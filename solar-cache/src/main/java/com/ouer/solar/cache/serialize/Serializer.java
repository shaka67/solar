/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.serialize;

import java.io.IOException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Serializer {

	public byte[] serialize(Object obj) throws IOException ;

	public Object deserialize(byte[] bytes) throws IOException ;
}
