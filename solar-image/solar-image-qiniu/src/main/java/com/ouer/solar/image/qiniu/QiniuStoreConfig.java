/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image.qiniu;

import java.io.IOException;

import com.ouer.solar.protocol.GsonObjectConverter;
import com.ouer.solar.protocol.ObjectConverter;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class QiniuStoreConfig {

	// TODO

	public static void main(String[] args) throws IOException {
		final ObjectConverter converter = new GsonObjectConverter();
		final String string = converter.toString(new QiniuStoreConfig());
		System.out.println(string);
		System.out.println(converter.fromString(string, QiniuStoreConfig.class));
	}
}
